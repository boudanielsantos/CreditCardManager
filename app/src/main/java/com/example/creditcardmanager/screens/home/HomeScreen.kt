package com.example.creditcardmanager.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.creditcardmanager.components.CardLogo
import com.example.creditcardmanager.components.CreditCardManagerAppBar
import com.example.creditcardmanager.components.FABContent
import com.example.creditcardmanager.model.CreditCard
import com.example.creditcardmanager.model.CreditCardStatus
import com.example.creditcardmanager.model.CreditCardType
import com.example.creditcardmanager.utils.Utils
import java.util.Date

@Composable
fun HomeScreen(onNavigateToAddCard: () -> Unit, onNavigateToUpdateCard: (Int) -> Unit) {
    Scaffold(topBar = {
        CreditCardManagerAppBar(title = "Credit Card Manager", showHome = true, icon = null)
    }, floatingActionButton = {
        FABContent { onNavigateToAddCard() }
    }) { innerPadding ->
        HomeContent(innerPadding, onNavigateToUpdateCard)
    }
}

@Composable
fun HomeContent(paddingValues: PaddingValues, onNavigateToUpdateCard: (Int) -> Unit) {
    Column(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //TODO LOAD AVAILABLE CARDS FROM DB


        val cardList = mutableListOf(
            CreditCard(
                id = 1,
                cardName = "BDO VISA",
                description = "Card for collecting points",
                creditLimit = 1500000.00,
                lastFourDigits = "5555",
                expiryDate = "12/2023",
                dueDay = 5,
                statementDay = 6,
                cardType = CreditCardType.DINERS_CLUB_INTERNATIONAL,
                cardStatus = CreditCardStatus.UNPAID,
            ),
            CreditCard(
                id = 2,
                cardName = "Chinabank",
                description = "Card for dining",
                creditLimit = 300000.00,
                lastFourDigits = "5325",
                expiryDate = "22/2025",
                dueDay = 1,
                statementDay = 5 ,
                cardType = CreditCardType.JCB,
                cardStatus = CreditCardStatus.SETTLED
            )
        )
        LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            items(items = cardList) { card ->
                CardItem(card, onNavigateToUpdateCard)
            }
        }

    }
}

@Preview
@Composable
fun CardItem(
    card: CreditCard = CreditCard(
        cardName = "BDO VISA",
        description = "Card for collecting points",
        creditLimit = 1500000.00,
        lastFourDigits = "5555",
        expiryDate = "12312",
        dueDay = 5,
        statementDay = 5,
        cardType = CreditCardType.DINERS_CLUB_INTERNATIONAL,
        cardStatus = CreditCardStatus.UNPAID,
    ),
    onNavigateToUpdateCard: (Int) -> Unit = {}
) {
    val cardIcon = remember {
        mutableStateOf(0)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .clickable {
                onNavigateToUpdateCard(card.id!!)
            },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(10.dp),
        border = BorderStroke(3.dp, color = Color.DarkGray.copy(0.5f))
    ) {
        Column {
            cardIcon.value = CreditCardType.getCardTypeIcon(card.cardType)
            Row(modifier = Modifier.fillMaxWidth()) {
                CardLogo(cardIcon.value)
                Spacer(modifier = Modifier.width(20.dp))
                CardSummary(card)
            }
        }
    }
}

@Composable
fun CardSummary(card: CreditCard) {
    Column(modifier = Modifier.padding(10.dp)) {
        Text(
            text = card.cardName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            fontSize = 20.sp,
            overflow = TextOverflow.Ellipsis
        )

        Text(modifier = Modifier.padding(bottom = 5.dp), text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                append(text = "Statement Date: ")
            }
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append(card.statementDay.toString())
            }
        })
        Text(modifier = Modifier.padding(bottom = 5.dp), text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                append(text = "Due Date: ")
            }
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append(card.dueDay.toString())
            }
        })



        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                    append("Status:")
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.ExtraBold,
                        color = if (card.cardStatus == CreditCardStatus.SETTLED) Color.Green else Color.Red
                    )
                ) {
                    append(card.cardStatus.toString())
                }
            },
        )

    }

}


