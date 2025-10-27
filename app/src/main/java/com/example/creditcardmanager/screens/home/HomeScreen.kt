package com.example.creditcardmanager.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.creditcardmanager.R
import com.example.creditcardmanager.components.CreditCardManagerAppBar
import com.example.creditcardmanager.components.FABContent
import com.example.creditcardmanager.model.CreditCard
import com.example.creditcardmanager.model.CreditCardStatus
import com.example.creditcardmanager.model.CreditCardType
import java.util.Date

@Composable
fun HomeScreen(onNavigateToAddCard: () -> Unit) {
    Scaffold(topBar = {
        CreditCardManagerAppBar(title = "Credit Card Manager", showHome = true, icon = null)
    }, floatingActionButton = {
        FABContent { onNavigateToAddCard() }
    }) { innerPadding ->
        HomeContent(innerPadding)
    }
}

@Composable
fun HomeContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //TODO LOAD AVAILABLE CARDS FROM DB


        CardItem()
    }
}

@Preview
@Composable
fun CardItem(
    card: CreditCard = CreditCard(
        cardName = "BDO VISA",
        description = null,
        creditLimit = 1500000.00,
        lastFourDigits = "5555",
        expiryDate = Date(),
        dueDate = Date(),
        statementDate = Date(),
        cardType = CreditCardType.VISA,
        cardStatus = CreditCardStatus.NOT_PAID
    )
) {
    val cardIcon = remember {
        mutableStateOf(0)
    }

    Card(
        modifier = Modifier
            .height(150.dp)
            .width(250.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        border = BorderStroke(1.dp, color = Color.LightGray)
    ) {
        Column {

            cardIcon.value = getCardTypeIcon(card.cardType)
            Row(modifier = Modifier.fillMaxWidth()) {
                Image(painterResource(cardIcon.value), contentDescription = "Credit Card Icon")
            }
        }
    }
}

private fun getCardTypeIcon(cardType: CreditCardType): Int =
    when (cardType) {
        CreditCardType.VISA -> R.drawable.visa
        CreditCardType.JCB -> R.drawable.jcb
        CreditCardType.MASTER_CARD -> R.drawable.mastercard
        CreditCardType.DISCOVER_CARD -> R.drawable.discover
        CreditCardType.DINERS_CLUB_INTERNATIONAL -> R.drawable.diners_club
        CreditCardType.AMERICAN_EXPRESS -> R.drawable.amex
        else -> R.drawable.others

    }
