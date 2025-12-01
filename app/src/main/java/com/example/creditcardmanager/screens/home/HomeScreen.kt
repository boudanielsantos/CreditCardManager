package com.example.creditcardmanager.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.creditcardmanager.R
import com.example.creditcardmanager.components.CardLogo
import com.example.creditcardmanager.components.CreditCardManagerAppBar
import com.example.creditcardmanager.components.FABContent
import com.example.creditcardmanager.components.ShowAlertDialog
import com.example.creditcardmanager.model.CreditCard
import com.example.creditcardmanager.model.CreditCardStatus
import com.example.creditcardmanager.model.CreditCardType
import com.example.creditcardmanager.ui.theme.BurnOrange
import com.example.creditcardmanager.ui.theme.FloralWhite
import com.example.creditcardmanager.ui.theme.Gold
import com.example.creditcardmanager.utils.Utils

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToAddCard: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToUpdateCard: (Int) -> Unit
) {
    val openDialogDeleteAllState = remember {
        mutableStateOf(false)
    }
    Scaffold(modifier = Modifier.background(Color.Black), topBar = {
        CreditCardManagerAppBar(
            title = "Credit Card Manager",
            showHome = true,
            icon = null,
            onNavigateToSettings = onNavigateToSettings,
            onDeleteAll = {
                openDialogDeleteAllState.value = true
            }
        )
    }, floatingActionButton = {
        FABContent { onNavigateToAddCard() }
    }) { innerPadding ->
        if (openDialogDeleteAllState.value) {
            ShowAlertDialog(
                title = stringResource(R.string.delete_dialog_title_all_card),
                message = stringResource(R.string.delete_dialog_message_all),
                openDialog = openDialogDeleteAllState
            ) {
                viewModel.deleteAllCreditCard()
                openDialogDeleteAllState.value = false
            }
        }

        Surface(color = Color(0xFF1F1616)) {
            HomeContent(
                innerPadding,
                viewModel = viewModel,
                onNavigateToUpdateCard = onNavigateToUpdateCard
            )
        }

    }
}

@Composable
fun HomeContent(
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToUpdateCard: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val cardList = viewModel.creditCards.collectAsState().value.data

        if (cardList.isNullOrEmpty()) {

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    "No Credit Cards Added",
                    color = Color.LightGray.copy(0.8f)
                )
            }

        } else {
            val openDialog = remember {
                mutableStateOf(false)
            }
            LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                items(items = cardList) { card ->
                    CardItem(
                        card,
                        onDeleteCardClicked = {
                            openDialog.value = true

                        },
                        onMarkAsSettledClicked = {
                            viewModel.updateCreditCard(card.copy(cardStatus = CreditCardStatus.SETTLED))
                        },
                        onNavigateToUpdateCard = onNavigateToUpdateCard
                    )
                    if (openDialog.value) {
                        ShowAlertDialog(
                            title = stringResource(R.string.delete_dialog_title),
                            message = stringResource(R.string.delete_dialog_message_single),
                            openDialog = openDialog
                        ) {
                            viewModel.deleteCreditCard(card)
                            openDialog.value = false
                        }
                    }
                }
            }
        }


    }


}

@Composable
fun CardItem(
    card: CreditCard,
    onMarkAsSettledClicked: () -> Unit = {},
    onDeleteCardClicked: () -> Unit = {},
    onNavigateToUpdateCard: (Int) -> Unit = {}
) {
    val cardIcon = remember {
        mutableStateOf(0)
    }
    val showMenu = remember {
        mutableStateOf(false)
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
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(BurnOrange)) {
                CardLogo(cardIcon.value)
                Spacer(modifier = Modifier.width(20.dp))
                CardSummary(card)
                ShowUpdateAndDeleteMenu(
                    showMenu, card.cardStatus,
                    onDeleteCardClicked = onDeleteCardClicked
                ) {
                    onMarkAsSettledClicked()
                }

            }
        }


    }
}

@Composable
fun ShowUpdateAndDeleteMenu(
    showMenu: MutableState<Boolean>, cardStatus: CreditCardStatus,
    onDeleteCardClicked: () -> Unit,
    onMarkAsSettledClicked: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.End) {
        IconButton(
            onClick = { showMenu.value = true },
            modifier = Modifier

                .padding(8.dp)
        ) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
        }

        DropdownMenu(
            expanded = showMenu.value,
            onDismissRequest = { showMenu.value = false }
        ) {

            if (cardStatus.name == CreditCardStatus.UNPAID.name) {
                DropdownMenuItem(
                    text = { Text("Mark as Settled") },
                    onClick = {
                        onMarkAsSettledClicked()
                        showMenu.value = false
                    }
                )
            }
            DropdownMenuItem(
                text = { Text("Delete") },
                onClick = {
                    onDeleteCardClicked()
                    showMenu.value = false
                }
            )

        }
    }
}

@Composable
fun CardSummary(card: CreditCard) {
    Column(modifier = Modifier.padding(10.dp)) {
        Text(
            text = card.cardName,
            color = FloralWhite,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            fontSize = 15.sp,
            overflow = TextOverflow.Clip
        )

        val statementMonth = Utils.getMonthName(card.currentStatementMonth)
        val dueMonth = Utils.getMonthName(card.currentDueMonth)

        Text(modifier = Modifier.padding(bottom = 5.dp), color =FloralWhite , text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                append(text = "Statement Date: ")
            }
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append("$statementMonth ${card.statementDay}")
            }
        })
        Text(modifier = Modifier.padding(bottom = 5.dp), color = FloralWhite, text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                append(text = "Due Date: ")
            }
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append("$dueMonth ${card.dueDay}")
            }
        })



        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Light, color = FloralWhite)) {
                    append("Status:  ")
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


