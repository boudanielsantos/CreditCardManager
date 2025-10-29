package com.example.creditcardmanager

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.creditcardmanager.data.CreditCardDao
import com.example.creditcardmanager.data.DateConverter
import com.example.creditcardmanager.data.SettingsDao
import com.example.creditcardmanager.model.CreditCard
import com.example.creditcardmanager.model.Settings

@Database(entities = [CreditCard::class, Settings::class], version = 4, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class CreditCardManagerDatabase : RoomDatabase() {

    abstract fun creditCardDao(): CreditCardDao

    abstract fun settingsDao(): SettingsDao
}