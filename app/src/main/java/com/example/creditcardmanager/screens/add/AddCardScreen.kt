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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.creditcardmanager.components.CreditCardManagerAppBar
import com.example.creditcardmanager.components.DropdownField
import com.example.creditcardmanager.components.InputField
import com.example.creditcardmanager.model.CreditCard
import com.example.creditcardmanager.model.CreditCardState
import com.example.creditcardmanager.model.CreditCardStatus
import com.example.creditcardmanager.model.CreditCardType
import com.example.creditcardmanager.utils.Constants

@Composable
fun AddCardScreen(viewModel: AddCardViewModel = hiltViewModel(), onNavigateToHome: () -> Unit) {

    val creditCardState = remember {
        mutableStateOf(CreditCardState())
    }

    Scaffold(topBar = {
        CreditCardManagerAppBar(
            title = "Add new card",
            showHome = false,
            showSave = true,
            onSaveClicked = {
                val creditCard = createCreditCard(creditCardState)
                viewModel.addCard(creditCard)
                onNavigateToHome()
            },
            icon = Icons.AutoMirrored.Filled.ArrowBack,
        ) {
            viewModel
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
            AddCardScreenContent(creditCardState)
        }
    }
}

@Composable
fun AddCardScreenContent(creditCardState: MutableState<CreditCardState>) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val daysInMonth = (1..31).map { it.toString() }

    InputField(
        label = "Card Name", isSingleLine = true,
        valueState = creditCardState.value.cardNameState,
        imeAction = ImeAction.Next,
    )

    InputField(
        label = "Card Description", isSingleLine = false,
        valueState = creditCardState.value.cardDescriptionState,
        imeAction = ImeAction.Next,
    )
    DropdownField(
        label = "Card Type",
        selectedValue = creditCardState.value.cardTypeState,
        options = CreditCardType.cardTypeNames
    )

    DropdownField(
        label = "Statement Date",
        selectedValue = creditCardState.value.statementDateState,
        options = daysInMonth
    )
    DropdownField(
        label = "Due Date",
        selectedValue = creditCardState.value.dueDateState,
        options = daysInMonth
    )

    InputField(
        label = "Last 4 Digits of Card Number", isSingleLine = false,
        valueState = creditCardState.value.lastFourDigitsState,
        imeAction = ImeAction.Next,
        maxCharacter = 4,
        keyboardType = KeyboardType.Number
    )

    InputField(
        label = "Credit Limit", isSingleLine = false,
        valueState = creditCardState.value.creditLimitState,
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Number
    )
    InputField(
        valueState = creditCardState.value.expiryDateState,
        label = "Expiry Date (MM/YY)",
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Done,
        maxCharacter = 4, //
        visualTransformation = ExpiryDateVisualTransformation(),
        onValueChange = { newValue ->
            if (newValue.length <= 4) {
                creditCardState.value.expiryDateState.value = newValue.filter { it.isDigit() }
            }
        }
    )
}

private fun createCreditCard(creditCardState: MutableState<CreditCardState>): CreditCard {

    return CreditCard(
        cardName = creditCardState.value.cardNameState.value,
        description = creditCardState.value.cardDescriptionState.value,
        creditLimit = creditCardState.value.creditLimitState.value.toDouble(),
        lastFourDigits = creditCardState.value.lastFourDigitsState.value,
        expiryDate = creditCardState.value.expiryDateState.value,
        dueDay = creditCardState.value.dueDateState.value.toInt(),
        statementDay = creditCardState.value.statementDateState.value.toInt(),
        cardType = CreditCardType.getCardType(creditCardState.value.cardTypeState.value),
        cardStatus = CreditCardStatus.UNPAID
    )
}