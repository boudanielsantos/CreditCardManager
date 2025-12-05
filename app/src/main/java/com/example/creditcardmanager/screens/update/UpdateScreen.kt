package com.example.creditcardmanager.screens.update

import android.widget.Toast
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
import com.example.creditcardmanager.components.ShowToast
import com.example.creditcardmanager.model.CreditCard
import com.example.creditcardmanager.model.CreditCardType

@Composable
fun UpdateScreen(
    updateCardViewModel: UpdateCardViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit
) {
    val showState = remember {
        mutableStateOf(false)
    }
    val cardState = updateCardViewModel.creditCard.collectAsState().value
    val isLoading = cardState.loading
    val creditCard = cardState.data
    if (isLoading) {
        CircularProgressIndicator()
    } else {
        val cardDescriptionValue = remember {
            mutableStateOf(creditCard?.description ?: "")
        }

        val cardCreditLimitValue = remember {
            val limit = creditCard?.creditLimit
            val formattedLimit = if (limit != null) {
                if (limit % 1.0 == 0.0) {
                    limit.toInt().toString()
                } else {
                    limit.toString()
                }
            } else {
                ""
            }
            mutableStateOf(formattedLimit)
        }

        val isValidState = remember(cardDescriptionValue.value, cardCreditLimitValue.value) {
            cardDescriptionValue.value.isNotEmpty() && cardCreditLimitValue.value.isNotEmpty()
        }
        Scaffold(
            topBar = {
                CreditCardManagerAppBar(
                    title = "Update ${creditCard?.cardName}",
                    showHome = false,
                    showSave = true,
                    isSaveEnabled = isValidState,
                    onSaveClicked = {
                        showState.value = true
                        updateCardViewModel.updateCard(
                            creditCard!!.copy(
                                description = cardDescriptionValue.value,
                                creditLimit = cardCreditLimitValue.value.toDouble()
                            )
                        )
                        onNavigateToHome()
                    },
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                ) {
                    onNavigateToHome()
                }
            }
        ) { innerPadding ->
            if (creditCard != null) {
                CardDetails(creditCard, innerPadding, cardDescriptionValue, cardCreditLimitValue)
            }

        }

        if (showState.value) {
            ShowToast(showState, "Card Updated Successfully", Toast.LENGTH_SHORT)
        }
    }

}

@Composable
fun CardDetails(
    card: CreditCard,
    innerPadding: PaddingValues,
    cardDescriptionValue: MutableState<String>,
    cardCreditLimitValue: MutableState<String>
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CardLogo(CreditCardType.getCardTypeIcon(card.cardType))
        Text(card.cardName, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)


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
            valueState = cardCreditLimitValue,
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        )

    }


}