package com.example.creditcardmanager.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.creditcardmanager.R
import com.example.creditcardmanager.ui.theme.LightBlue


@Composable
fun CreditCardManagerLogo() {
    Card(shape = CircleShape) {
        Image(
            painter = painterResource(R.drawable.splash_screen_credit_card_manager_logo),
            contentDescription = "Splash Screen Logo"
        )
    }

    Text(
        text = "Credit Card Manager",
        style = MaterialTheme.typography.headlineMedium,
        color = Color.White
    )


}

@Composable
fun FABContent(onTap: () -> Unit) {
    FloatingActionButton(
        onClick = { onTap() },
        shape = RoundedCornerShape(50.dp),
        containerColor = Color.White
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = LightBlue)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditCardManagerAppBar(
    title: String,
    icon: ImageVector?,
    showHome: Boolean = true,
    onBackArrowClicked: () -> Unit = {}
) {
    var showMenu = remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showHome) {
                    //TODO
                }
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "icon",
                        tint = Color.Red.copy(alpha = 0.7f),
                        modifier = Modifier.clickable {
                            onBackArrowClicked.invoke()
                        }
                    )
                }
                Spacer(modifier = Modifier.width(40.dp))
                Text(
                    text = title,
                    color = Color.Blue.copy(0.5f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.width(160.dp))


            }
        },
        actions = {
            Box(modifier = Modifier.wrapContentSize()) {
                IconButton(onClick = { showMenu.value = !showMenu.value }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "More options")
                }
                DropdownMenu(
                    expanded = showMenu.value,
                    onDismissRequest = { showMenu.value = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Settings") },
                        onClick = { /* Handle settings click */ showMenu.value = false }
                    )
                    DropdownMenuItem(
                        text = { Text("About") },
                        onClick = { /* Handle about click */ showMenu.value = false }
                    )
                    // Add more menu items as needed
                }
            }
        },
        modifier = Modifier.shadow(elevation = 0.dp),
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
    )
}