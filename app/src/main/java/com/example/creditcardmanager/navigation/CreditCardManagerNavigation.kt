package com.example.creditcardmanager.navigation

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.creditcardmanager.screens.add.AddCardScreen
import com.example.creditcardmanager.screens.home.HomeScreen
import com.example.creditcardmanager.screens.splash.SplashScreen

@Composable
fun CreditCardManagerNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = CreditCardScreens.SPLASH_SCREEN.name
    ) {
        composable(route = CreditCardScreens.SPLASH_SCREEN.name) {
            SplashScreen(
                navigateToHome = {
                    navController.navigate(CreditCardScreens.HOME_SCREEN.name)
                }
            )
        }
        composable(route = CreditCardScreens.HOME_SCREEN.name) {
            HomeScreen(onNavigateToAddCard = {
                navController.navigate(CreditCardScreens.ADD_CARD_SCREEN.name)
            })
        }
        composable(route = CreditCardScreens.ADD_CARD_SCREEN.name) {
            AddCardScreen()
        }
    }
}