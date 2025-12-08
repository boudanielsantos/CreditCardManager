package com.example.creditcardmanager

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.creditcardmanager.worker.notification.NotificationWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class CreditCardManagerApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()


    override fun onCreate() {
        super.onCreate()
        setupRecurringWork()
    }

    private fun setupRecurringWork() {
        val workManager = WorkManager.getInstance(applicationContext)

        val dailyWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.DAYS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "DailyNotificationWorker",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            dailyWorkRequest
        )
    }
}


