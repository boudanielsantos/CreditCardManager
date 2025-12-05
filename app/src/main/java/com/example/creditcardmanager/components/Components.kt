package com.example.creditcardmanager.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.creditcardmanager.R
import com.example.creditcardmanager.ui.theme.LightBlue

//Used in Splash Screen
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
        containerColor = Color.LightGray
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.Gray)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditCardManagerAppBar(
    title: String,
    icon: ImageVector?,
    showHome: Boolean = true,
    onSaveClicked: () -> Unit = {},
    showSave: Boolean = false,
    isSaveEnabled: Boolean = false,
    onNavigateToSettings: () -> Unit = {},
    onDeleteAll: () -> Unit = {},
    onBackArrowClicked: () -> Unit = {},

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
                        tint = Color.LightGray.copy(alpha = 0.8f),
                        modifier = Modifier.clickable {
                            onBackArrowClicked.invoke()
                        }
                    )
                }
                Spacer(modifier = Modifier.width(40.dp))
                Text(
                    text = title,
                    color = Color.White.copy(0.5f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.width(160.dp))


            }
        },
        actions = {
            if (showHome) {
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
                            onClick = {
                                showMenu.value = false
                                onNavigateToSettings()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Delete All") },
                            onClick = {
                                showMenu.value = false
                                onDeleteAll()
                            }
                        )
                    }
                }
            }
            if (showSave) {
                IconButton(
                    onClick = {
                        onSaveClicked()
                    },
                    enabled = isSaveEnabled,
                ) {
                    Text(
                        text = "Save",
                        color = if (isSaveEnabled) Color.White else Color.White.copy(0.2f)
                    )
                }

            }

        },
        modifier = Modifier.shadow(elevation = 0.dp),
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
    )
}

@Composable
fun CardLogo(cardIcon: Int) {
    Surface(
        color = Color.White,
        modifier = Modifier
            .padding(10.dp)
            .height(100.dp)
            .width(100.dp),
        shape = CircleShape,
        border = BorderStroke(3.dp, color = Color.LightGray.copy(0.6f))
    ) {
        Image(painterResource(cardIcon), contentDescription = "Credit Card Icon")

    }
}


@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    label: String,
    isRequired: Boolean = false,
    validState: MutableState<Boolean> = mutableStateOf(true),
    enabled: Boolean = true,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    maxCharacter: Int = 99,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onAction: KeyboardActions = KeyboardActions.Default,
    onValueChange: (String) -> Unit = { valueState.value = it }
) {

    OutlinedTextField(
        value = valueState.value,
        onValueChange = { newValue ->
            validState.value = !newValue.isEmpty()
            if (newValue.length <= maxCharacter) {
                onValueChange(newValue)
            }
        },
        isError = !validState.value,
        supportingText = {
            if (isRequired && !validState.value) {
                Text(text = "This field is required")
            }
        },
        label = { Text(text = label) },
        singleLine = isSingleLine,
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        visualTransformation = visualTransformation
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selectedValue: MutableState<String>,
    isRequired: Boolean = false,
    validState: MutableState<Boolean> = mutableStateOf(true)
) {
    var expanded by remember { mutableStateOf(false) }

    // Filter options based on the input text
    val filteredOptions = options.filter {
        it.contains(selectedValue.value, ignoreCase = true)
    }

    Column {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                supportingText = {
                    if (isRequired && !validState.value) {
                        Text("This field is required")
                    }
                },
                value = selectedValue.value,
                onValueChange = { newValue ->

                    validState.value = !newValue.isEmpty()
                    selectedValue.value = newValue
                    expanded = true
                },
                label = { Text(label) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor() // Important for anchoring the dropdown
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                if (filteredOptions.isEmpty()) {
                    DropdownMenuItem(
                        text = { Text("No results found") },
                        onClick = { expanded = false },
                        enabled = false
                    )
                } else {
                    filteredOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                validState.value = !option.isEmpty()
                                selectedValue.value = option
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShowAlertDialog(
    title: String,
    message: String,
    openDialog: MutableState<Boolean>,
    onYesPressed: () -> Unit
) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = title) },
            text = { Text(text = message) },
            dismissButton = {
                TextButton(onClick = { openDialog.value = false }) { Text("No") }
            },
            confirmButton = {
                TextButton(onClick = onYesPressed) { Text("Yes") }
            })
    }

}

@Composable
fun InputSwitch(
    label: String, checkedState: MutableState<Boolean>,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 15.sp
        )
        Switch(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = it
                onCheckedChange(it)
            }
        )
    }

}

@Composable
fun ShowToast(showState: MutableState<Boolean>, message: String, length: Int) {
    Toast.makeText(LocalContext.current, message, length).show()
}

