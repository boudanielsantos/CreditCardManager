package com.example.creditcardmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.creditcardmanager.model.CreditCard
import kotlinx.coroutines.flow.Flow

@Dao
interface CreditCardDao {

    @Query("SELECT * FROM credit_card")
    fun getAllCreditCards(): Flow<List<CreditCard>>

    @Insert
    suspend fun createCreditCard(creditCard: CreditCard)


    @Delete
    suspend fun deleteCreditCard(creditCard: CreditCard)

    @Query("DELETE from credit_card")
    suspend fun deleteAllCreditCards()

    @Update
    suspend fun updateCreditCard(creditCard: CreditCard)

}