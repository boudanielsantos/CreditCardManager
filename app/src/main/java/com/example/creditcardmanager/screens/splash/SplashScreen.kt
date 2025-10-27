package com.example.creditcardmanager.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.creditcardmanager.components.CreditCardManagerLogo
import com.example.creditcardmanager.ui.theme.LightBlue
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navigateToHome: () -> Unit) {
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(0.9f, animationSpec = tween(durationMillis = 800, easing = {
            OvershootInterpolator(8f).getInterpolation(it)
        }))
        delay(2000)
        navigateToHome()
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier
                .size(350.dp)
                .scale(scale.value),
            shape = CircleShape,
            color = LightBlue,
            border = BorderStroke(width = 3.dp, color = Color.LightGray)
        ) {
            Column(
                modifier = Modifier.padding(1.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CreditCardManagerLogo()
            }
        }

    }
}