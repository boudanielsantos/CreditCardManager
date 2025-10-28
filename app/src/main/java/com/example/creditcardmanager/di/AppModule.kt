package com.example.creditcardmanager.di

import android.content.Context
import androidx.room.Room
import com.example.creditcardmanager.CreditCardManagerDatabase
import com.example.creditcardmanager.data.CreditCardDao
import com.example.creditcardmanager.repository.CreditCardRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): CreditCardManagerDatabase =
        Room.databaseBuilder(
            context,
            CreditCardManagerDatabase::class.java, "credit_card_database"
        ).fallbackToDestructiveMigration(true)
            .build()

    @Provides
    @Singleton
    fun provideCreditCardDao(creditCardAppDatabase: CreditCardManagerDatabase): CreditCardDao =
        creditCardAppDatabase.creditCardDao()

    @Provides
    @Singleton
    fun provideCreditCardRepository(creditCardDao: CreditCardDao) =
        CreditCardRepository(creditCardDao)
}


