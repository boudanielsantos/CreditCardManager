package com.example.creditcardmanager.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class CreditCardState(
    val cardTypeState: MutableState<String> = mutableStateOf(""),
    val cardTypeValidState: MutableState<Boolean> = mutableStateOf(false),
    val cardNameState: MutableState<String> = mutableStateOf(""),
    val cardNameValidState: MutableState<Boolean> = mutableStateOf(false),
    val cardDescriptionState: MutableState<String> = mutableStateOf(""),
    val lastFourDigitsState: MutableState<String> = mutableStateOf(""),
    val lastFourDigitsValidState: MutableState<Boolean> = mutableStateOf(false),
    val expiryDateState: MutableState<String> = mutableStateOf(""),
    val expiryDateValidState: MutableState<Boolean> = mutableStateOf(false),
    val creditLimitState: MutableState<String> = mutableStateOf(""),
    val creditLimitValidState: MutableState<Boolean> = mutableStateOf(false),
    val dueDateState: MutableState<String> = mutableStateOf(""),
    val dueDateValidState: MutableState<Boolean> = mutableStateOf(false),
    val statementDateValidState: MutableState<Boolean> = mutableStateOf(false),
    val statementDateState: MutableState<String> = mutableStateOf("")
)
