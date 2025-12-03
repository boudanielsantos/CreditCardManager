package com.example.creditcardmanager

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
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
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val myWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setConstraints(constraints)
            // For periodic work, you would use PeriodicWorkRequestBuilder
            // For example, run once every 24 hours:
            // val myWorkRequest = PeriodicWorkRequestBuilder<MyWorker>(24, TimeUnit.HOURS)
            //     .setConstraints(constraints)
            //     .build()
            .build()

        WorkManager.getInstance(this).enqueue(myWorkRequest)
    }
}


