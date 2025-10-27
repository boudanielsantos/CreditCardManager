package com.example.creditcardmanager

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.creditcardmanager.data.CreditCardDao
import com.example.creditcardmanager.data.DateConverter
import com.example.creditcardmanager.model.CreditCard

@Database(entities = [CreditCard::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class CreditCardManagerDatabase : RoomDatabase() {

    abstract fun creditCardDao(): CreditCardDao
}