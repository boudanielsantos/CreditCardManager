package com.example.creditcardmanager.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class CreditCardState(
    val cardTypeState: MutableState<String> = mutableStateOf(""),
    val cardNameState: MutableState<String> = mutableStateOf(""),
    val cardDescriptionState: MutableState<String> = mutableStateOf(""),
    val lastFourDigitsState: MutableState<String> = mutableStateOf(""),
    val cardNumberState: MutableState<String> = mutableStateOf(""),
    val expiryDateState: MutableState<String> = mutableStateOf(""),
    val creditLimitState: MutableState<String> = mutableStateOf(""),
    val dueDateState: MutableState<String> = mutableStateOf(""),
    val statementDateState: MutableState<String> = mutableStateOf("")
)
