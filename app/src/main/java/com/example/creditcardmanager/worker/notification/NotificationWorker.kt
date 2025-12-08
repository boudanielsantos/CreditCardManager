package com.example.creditcardmanager.worker.notification

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.creditcardmanager.model.CreditCard
import com.example.creditcardmanager.repository.CreditCardRepository
import com.example.creditcardmanager.repository.SettingsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.util.Calendar

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val creditCardRepository: CreditCardRepository,
    private val settingsRepository: SettingsRepository
) : CoroutineWorker(appContext, workerParams) {


    override suspend fun doWork(): Result {

        try {
            val settings = settingsRepository.getSettings().first()

            val calendar = Calendar.getInstance()
            val today = calendar.get(Calendar.DAY_OF_MONTH)


            if (settings.allowSendingOfStatementDateNotification) {
                val cardWithStatementDateToday =
                    creditCardRepository.getCardsByStatementDate(today).first()
                cardWithStatementDateToday.forEach { card ->
                    sendNotification(
                        card = card,
                        title = "Statement Date",
                        message = "Your card ${card.cardName} has a statement date today"
                    )
                }
            }

            if (settings.allowSendingOfDueDateNotification) {
                val cardsWithDueDateToday = creditCardRepository.getCardsByDueDate(today).first()
                cardsWithDueDateToday.forEach { card ->
                    sendNotification(
                        card = card,
                        title = "Due Date Reminder",
                        message = "Your payment for ${card.cardName} is due today."
                    )
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error Encountered when sending notification :$ex")
            return Result.failure()
        }

        return Result.success()
    }

    private fun sendNotification(card: CreditCard, title: String, message: String) {
        val notificationHelper = NotificationHelper(applicationContext)
        notificationHelper.sendNotification(
            notificationId = card.id!!,
            title = title,
            message = message
        )
    }

    companion object {
        private val TAG = NotificationWorker::class.java.simpleName
    }

}


