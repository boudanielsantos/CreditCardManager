package com.example.creditcardmanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Settings(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var allowSendingOfDueDateNotification: Boolean,
    var allowSendingOfStatementDateNotification: Boolean
)