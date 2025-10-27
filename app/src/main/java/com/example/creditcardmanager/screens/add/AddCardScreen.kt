package com.example.creditcardmanager.screens.add

import ExpiryDateVisualTransformation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.creditcardmanager.components.CreditCardManagerAppBar
import com.example.creditcardmanager.components.DropdownField
import com.example.creditcardmanager.components.InputField
import com.example.creditcardmanager.model.CreditCardType
import com.example.creditcardmanager.utils.Constants

@Composable
fun AddCardScreen(onNavigateToHome: () -> Unit) {
    Scaffold(topBar = {
        CreditCardManagerAppBar(
            title = "Add new card",
            showHome = false,
            showSave = true,
            icon = Icons.AutoMirrored.Filled.ArrowBack
        ) {
            onNavigateToHome()
        }

    }) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            AddCardScreenContent()
        }
    }
}

@Composable
fun AddCardScreenContent() {
    val keyboardController = LocalSoftwareKeyboardController.current

    val cardNameState = rememberSaveable { mutableStateOf("") }
    val cardDescriptionState = rememberSaveable { mutableStateOf("") }
    val cardTypeState = rememberSaveable { mutableStateOf("") }
    val lastFourDigitsState = rememberSaveable { mutableStateOf("") }
    val creditLimitState = rememberSaveable { mutableStateOf("") }
    val expiryDateState = rememberSaveable { mutableStateOf("") }
    val statementDateState = rememberSaveable { mutableStateOf("") }
    val dueDateState = rememberSaveable { mutableStateOf("") }
    val daysInMonth = (1..31).map { it.toString() }

    InputField(
        label = "Card Name", isSingleLine = true,
        valueState = cardNameState,
        imeAction = ImeAction.Next,
    )

    InputField(
        label = "Card Description", isSingleLine = false,
        valueState = cardDescriptionState,
        imeAction = ImeAction.Next,
    )
    DropdownField(
        label = "Card Type",
        selectedValue = cardTypeState,
        options = CreditCardType.cardTypeNames
    )

    DropdownField(
        label = "Statement Date",
        selectedValue = statementDateState,
        options = daysInMonth
    )
    DropdownField(
        label = "Due Date",
        selectedValue = dueDateState,
        options = daysInMonth
    )

    InputField(
        label = "Last 4 Digits of Card Number", isSingleLine = false,
        valueState = lastFourDigitsState,
        imeAction = ImeAction.Next,
        maxCharacter = 4,
        keyboardType = KeyboardType.Number
    )

    InputField(
        label = "Credit Limit", isSingleLine = false,
        valueState = creditLimitState,
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Number
    )
    InputField(
        valueState = expiryDateState,
        label = "Expiry Date (MM/YY)",
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Done,
        maxCharacter = 4, //
        visualTransformation = ExpiryDateVisualTransformation(),
        onValueChange = { newValue ->
            if (newValue.length <= 4) {
                expiryDateState.value = newValue.filter { it.isDigit() }
            }
        }
    )
}