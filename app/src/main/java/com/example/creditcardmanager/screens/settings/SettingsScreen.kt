package com.example.creditcardmanager.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.creditcardmanager.components.CreditCardManagerAppBar
import com.example.creditcardmanager.components.InputSwitch

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    onNavigateBackToHome: () -> Unit = {}
) {
    SettingsContent(settingsViewModel) { onNavigateBackToHome() }
}

@Composable
fun SettingsContent(settingsViewModel: SettingsViewModel, onNavigateBackToHome: () -> Unit) {
    val settingsFromDb = settingsViewModel.settings.collectAsState().value.data
    val sendingOfDueDateNotificationState = remember {
        mutableStateOf(false)
    }
    val sendingOfStatementDateNotificationState = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            CreditCardManagerAppBar(
                title = "Settings",
                showHome = false,
                icon = Icons.AutoMirrored.Filled.ArrowBack
            ) {
                onNavigateBackToHome()
            }
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                InputSwitch(
                    label = "Send notification on due date?",
                    sendingOfDueDateNotificationState
                ) {

                }
                InputSwitch(
                    label = "Send notification on statement date?",
                    sendingOfStatementDateNotificationState
                ) {

                }

            }

        }
    }
}