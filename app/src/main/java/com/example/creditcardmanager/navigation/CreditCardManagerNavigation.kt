package com.example.creditcardmanager.navigation

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.creditcardmanager.screens.add.AddCardScreen
import com.example.creditcardmanager.screens.add.AddCardViewModel
import com.example.creditcardmanager.screens.home.HomeScreen
import com.example.creditcardmanager.screens.home.HomeViewModel
import com.example.creditcardmanager.screens.settings.SettingsScreen
import com.example.creditcardmanager.screens.settings.SettingsViewModel
import com.example.creditcardmanager.screens.splash.SplashScreen
import com.example.creditcardmanager.screens.update.UpdateCardViewModel
import com.example.creditcardmanager.screens.update.UpdateScreen

@Composable
fun CreditCardManagerNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = CreditCardScreens.HOME_SCREEN.name
    ) {
        composable(route = CreditCardScreens.SPLASH_SCREEN.name) {
            SplashScreen(
                navigateToHome = {
                    navController.navigate(CreditCardScreens.HOME_SCREEN.name)
                }
            )
        }
        composable(route = CreditCardScreens.HOME_SCREEN.name) {
            val homeViewModel = hiltViewModel<HomeViewModel>()

            HomeScreen(
                viewModel = homeViewModel,
                onNavigateToAddCard = {
                    navController.navigate(CreditCardScreens.ADD_CARD_SCREEN.name)
                },
                onNavigateToUpdateCard = { cardId, cardName ->
                    navController.navigate("${CreditCardScreens.UPDATE_CARD_SCREEN.name}/$cardId/$cardName")
                },
                onNavigateToSettings = {
                    navController.navigate(CreditCardScreens.SETTINGS_SCREEN.name)
                }
            )
        }

        composable(route = CreditCardScreens.ADD_CARD_SCREEN.name) {
            val addCardViewModel = hiltViewModel<AddCardViewModel>()

            AddCardScreen(
                onNavigateToHome = {
                    navController.navigate(CreditCardScreens.HOME_SCREEN.name)
                }, viewModel = addCardViewModel
            )
        }

        val route = CreditCardScreens.UPDATE_CARD_SCREEN.name
        composable(
            route = "$route/{cardId}/{cardName}",
            arguments = listOf(
                navArgument("cardId") {
                    type = NavType.IntType
                },
                navArgument("cardName") {
                    type = NavType.StringType
                }
            )) {
            val cardId = it.arguments?.getInt("cardId")
            val cardName = it.arguments?.getString("cardName")
            if (cardId != null && cardName != null) {

                val updateCardViewModel = hiltViewModel<UpdateCardViewModel>()
                UpdateScreen(updateCardViewModel) {
                    navController.navigate(CreditCardScreens.HOME_SCREEN.name)
                }
            }
        }
        composable(
            route = CreditCardScreens.SETTINGS_SCREEN.name
        ) {
            val settingsViewModel = hiltViewModel<SettingsViewModel>()
            SettingsScreen(
                settingsViewModel,
                onNavigateBackToHome = {
                    navController.navigate(CreditCardScreens.HOME_SCREEN.name)
                })
        }
    }
}