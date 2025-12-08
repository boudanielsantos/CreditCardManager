package com.example.creditcardmanager.screens.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.creditcardmanager.components.CreditCardManagerAppBar
import com.example.creditcardmanager.components.InputSwitch
import com.example.creditcardmanager.components.ShowToast

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    onNavigateBackToHome: () -> Unit = {}
) {
    SettingsContent(settingsViewModel) { onNavigateBackToHome() }
}

@Composable
fun SettingsContent(settingsViewModel: SettingsViewModel, onNavigateBackToHome: () -> Unit) {
    val settingsState = settingsViewModel.settings.collectAsState().value
    val settingsFromDb = settingsState.data

    if (!settingsState.loading) {

        val sendingOfDueDateNotificationState = remember {
            if (settingsFromDb != null) {
                mutableStateOf(settingsFromDb.allowSendingOfDueDateNotification)
            } else {
                mutableStateOf(false)
            }
        }
        val sendingOfStatementDateNotificationState = remember {
            if (settingsFromDb != null) {
                mutableStateOf(settingsFromDb.allowSendingOfStatementDateNotification)
            } else {
                mutableStateOf(false)
            }
        }
        val showState = remember {
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
                    if (showState.value) {
                        ShowToast(showState, "Settings Updated", Toast.LENGTH_SHORT)
                    }

                    InputSwitch(
                        label = "Send notification on due date?",
                        sendingOfDueDateNotificationState
                    ) {
                        if (settingsFromDb != null) {
                            settingsFromDb.allowSendingOfDueDateNotification =
                                sendingOfDueDateNotificationState.value
                            settingsViewModel.updateSettings(settingsFromDb)
                        }
                        showState.value = true
                    }
                    InputSwitch(
                        label = "Send notification on statement date?",
                        sendingOfStatementDateNotificationState
                    ) {
                        if (settingsFromDb != null) {
                            settingsFromDb.allowSendingOfStatementDateNotification =
                                sendingOfStatementDateNotificationState.value
                            settingsViewModel.updateSettings(settingsFromDb)
                        }
                        showState.value = true
                    }
                    showState.value = false
                }

            }
        }
    }

}