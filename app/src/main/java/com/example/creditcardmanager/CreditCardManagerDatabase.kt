package com.example.creditcardmanager

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.creditcardmanager.data.CreditCardDao
import com.example.creditcardmanager.data.DateConverter
import com.example.creditcardmanager.data.SettingsDao
import com.example.creditcardmanager.model.CreditCard
import com.example.creditcardmanager.model.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider

@Database(entities = [CreditCard::class, Settings::class], version = 5, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class CreditCardManagerDatabase : RoomDatabase() {

    abstract fun creditCardDao(): CreditCardDao

    abstract fun settingsDao(): SettingsDao

    class DatabaseCallback(
        private val settingsDaoProvider: Provider<SettingsDao>
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                populateDatabase()
            }
        }

        suspend fun populateDatabase() {
            val defaultSettings = Settings(
                id = 1,
                allowSendingOfDueDateNotification = true,
                allowSendingOfStatementDateNotification = true
            )
            // Insert the default settings into the database
            settingsDaoProvider.get().createSettings(defaultSettings)
        }
    }


}