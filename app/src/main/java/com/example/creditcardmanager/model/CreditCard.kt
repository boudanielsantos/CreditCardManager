package com.example.creditcardmanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "credit_card")
data class CreditCard(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val cardName: String,
    val description: String?,
    val creditLimit: Double,
    val lastFourDigits: String,
    val expiryDate: String,
    val dueDay: Int,
    val statementDay: Int,
    val cardType: CreditCardType,
    val cardStatus: CreditCardStatus
)
