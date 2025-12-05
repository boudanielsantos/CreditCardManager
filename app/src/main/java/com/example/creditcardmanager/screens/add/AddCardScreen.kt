package com.example.creditcardmanager.screens.add

import ExpiryDateVisualTransformation
import android.icu.util.Calendar
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.creditcardmanager.components.CreditCardManagerAppBar
import com.example.creditcardmanager.components.DropdownField
import com.example.creditcardmanager.components.InputField
import com.example.creditcardmanager.components.ShowToast
import com.example.creditcardmanager.model.CreditCard
import com.example.creditcardmanager.model.CreditCardState
import com.example.creditcardmanager.model.CreditCardStatus
import com.example.creditcardmanager.model.CreditCardType
import java.util.Date

@Composable
fun AddCardScreen(viewModel: AddCardViewModel = hiltViewModel(), onNavigateToHome: () -> Unit) {

    val creditCardState = remember {
        mutableStateOf(CreditCardState())
    }
    val showState = remember {
        mutableStateOf(false)
    }
    val isValidState = remember(
        creditCardState.value.lastFourDigitsState.value,
        creditCardState.value.cardNameState.value,
        creditCardState.value.creditLimitState.value,
        creditCardState.value.expiryDateState.value,
        creditCardState.value.cardTypeState.value,
        creditCardState.value.statementDateState.value,
        creditCardState.value.dueDateState.value
    ) {
        creditCardState.value.cardNameState.value.isNotEmpty() &&
                creditCardState.value.lastFourDigitsState.value.isNotEmpty() &&
                creditCardState.value.expiryDateState.value.isNotEmpty()
                && creditCardState.value.cardTypeState.value.isNotEmpty() &&
                creditCardState.value.statementDateState.value.isNotEmpty() && creditCardState.value.dueDateState.value.isNotEmpty()
    }


    Scaffold(topBar = {
        CreditCardManagerAppBar(
            title = "Add new card",
            showHome = false,
            showSave = true,
            isSaveEnabled = isValidState,
            onSaveClicked = {
                showState.value = true
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
    if (showState.value) {
        ShowToast(showState, "Card Added Successfully", Toast.LENGTH_SHORT)
    }
}

@Composable
fun AddCardScreenContent(creditCardState: MutableState<CreditCardState>) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val daysInMonth = (1..31).map { it.toString() }

    InputField(
        label = "Card Name", isSingleLine = true,
        isRequired = true,
        validState = creditCardState.value.cardNameValidState,
        valueState = creditCardState.value.cardNameState,
        imeAction = ImeAction.Next,
    )

    InputField(
        isRequired = false,
        label = "Card Description", isSingleLine = false,
        valueState = creditCardState.value.cardDescriptionState,
        imeAction = ImeAction.Next,
    )
    DropdownField(
        label = "Card Type",
        selectedValue = creditCardState.value.cardTypeState,
        options = CreditCardType.cardTypeNames,
        isRequired = true,
        validState = creditCardState.value.cardTypeValidState
    )

    DropdownField(
        label = "Statement Date",
        selectedValue = creditCardState.value.statementDateState,
        options = daysInMonth,
        isRequired = true,
        validState = creditCardState.value.statementDateValidState
    )
    DropdownField(
        label = "Due Date",
        selectedValue = creditCardState.value.dueDateState,
        options = daysInMonth,
        isRequired = true,
        validState = creditCardState.value.dueDateValidState
    )

    InputField(
        isRequired = true,
        validState = creditCardState.value.lastFourDigitsValidState,
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
        isRequired = true,
        validState = creditCardState.value.expiryDateValidState,
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
    val statementDay = creditCardState.value.statementDateState.value.toInt()
    val dueDay = creditCardState.value.dueDateState.value.toInt()

    val today = Calendar.getInstance()
    val currentDayOfMonth = today.get(Calendar.DAY_OF_MONTH)

    val statementCal = Calendar.getInstance()
    val dueCal = Calendar.getInstance()


    if (dueDay >= statementDay) {
        if (currentDayOfMonth < statementDay) {
            statementCal.add(Calendar.MONTH, -1)
            dueCal.add(Calendar.MONTH, -1)
        }
    } else {
        if (currentDayOfMonth >= statementDay) {
            dueCal.add(Calendar.MONTH, 1)
        } else {
            statementCal.add(Calendar.MONTH, -1)
        }
    }

    val currentStatementMonth =
        statementCal.get(Calendar.MONTH) + 1
    val currentDueMonth = dueCal.get(Calendar.MONTH) + 1

    return CreditCard(
        cardName = creditCardState.value.cardNameState.value,
        description = creditCardState.value.cardDescriptionState.value,
        creditLimit = if (creditCardState.value.creditLimitState.value.isNotEmpty()) creditCardState.value.creditLimitState.value.toDouble() else null,
        lastFourDigits = creditCardState.value.lastFourDigitsState.value,
        expiryDate = creditCardState.value.expiryDateState.value,
        dueDay = dueDay,
        statementDay = statementDay,
        cardType = CreditCardType.getCardType(creditCardState.value.cardTypeState.value),
        cardStatus = CreditCardStatus.UNPAID,
        lastUpdateDate = Date(),
        currentDueMonth = currentDueMonth,
        currentStatementMonth = currentStatementMonth
    )
}