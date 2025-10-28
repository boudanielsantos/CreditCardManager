package com.example.creditcardmanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "credit_card")
data class CreditCard(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    var cardName: String,
    var description: String?,
    var creditLimit: Double,
    var lastFourDigits: String,
    var expiryDate: String,
    var dueDay: Int,
    var statementDay: Int,
    var cardType: CreditCardType,
    var cardStatus: CreditCardStatus
)
