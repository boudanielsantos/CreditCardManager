package com.example.creditcardmanager.repository

import com.example.creditcardmanager.data.CreditCardDao
import com.example.creditcardmanager.model.CreditCard
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreditCardRepository @Inject constructor(val creditCardDao: CreditCardDao) {


    fun getAllCreditCards(): Flow<List<CreditCard>> = creditCardDao.getAllCreditCards()

    suspend fun insertCreditCard(creditCard: CreditCard) =
        creditCardDao.createCreditCard(creditCard)

    suspend fun deleteCreditCard(creditCard: CreditCard) =
        creditCardDao.deleteCreditCard(creditCard)

    suspend fun deleteAllCreditCards() =
        creditCardDao.deleteAllCreditCards()

    suspend fun updateCreditCard(creditCard: CreditCard) =
        creditCardDao.updateCreditCard(creditCard)

    fun getCardsByStatementDate(dayOfMonth: Int): Flow<List<CreditCard>> {
        return creditCardDao.getCardsByStatementDate(dayOfMonth)
    }

    fun getCardsByDueDate(dayOfMonth: Int): Flow<List<CreditCard>> {
        return creditCardDao.getCardsByDueDate(dayOfMonth)
    }

}