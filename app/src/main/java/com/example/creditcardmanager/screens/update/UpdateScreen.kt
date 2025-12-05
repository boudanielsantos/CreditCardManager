package com.example.creditcardmanager.screens.update

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.creditcardmanager.components.CardLogo
import com.example.creditcardmanager.components.CreditCardManagerAppBar
import com.example.creditcardmanager.components.CreditCardManagerLogo
import com.example.creditcardmanager.components.InputField
import com.example.creditcardmanager.model.CreditCard
import com.example.creditcardmanager.model.CreditCardType

@Composable
fun UpdateScreen(
    updateCardViewModel: UpdateCardViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit
) {

    val cardState = updateCardViewModel.creditCard.collectAsState().value
    val isLoading = cardState.loading
    val creditCard = cardState.data
    Scaffold(
        topBar = {
            CreditCardManagerAppBar(
                title = "Update ${creditCard?.cardName}",
                showHome = false,
                showSave = true,
//                isSaveEnabled = isValidState,
//                onSaveClicked = {
//                    val creditCard = createCreditCard(creditCardState)
//                    viewModel.addCard(creditCard)
//                    onNavigateToHome()
//                },
                icon = Icons.AutoMirrored.Filled.ArrowBack,
            ) {
                onNavigateToHome()
            }
        }
    ) { innerPadding ->
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            if (creditCard != null) {
                CardDetails(creditCard, innerPadding)
            }
        }
    }
}

@Composable
fun CardDetails(card: CreditCard, innerPadding: PaddingValues, onSaveClicked: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CardLogo(CreditCardType.getCardTypeIcon(card.cardType))
        Text(card.cardName, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
        val cardDescriptionValue = remember {
            mutableStateOf(card.description ?: "")
        }

        val cardCreditLimit = remember {
            mutableStateOf(if (card.creditLimit.toString() != "null") card.creditLimit.toString() else "")
        }


        InputField(
            label = "Card Description",
            isSingleLine = false,
            valueState = cardDescriptionValue,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text
        )

        InputField(
            label = "Credit Limit",
            isSingleLine = true,
            valueState = cardCreditLimit,
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        )

    }


}