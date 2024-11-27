package com.example.inzynierka1.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.inzynierka1.R
import com.example.inzynierka1.ui.components.DisplayInfo
import com.example.inzynierka1.ui.components.SimpleButton
import com.example.inzynierka1.viewmodels.MainViewModel

@Composable
fun MenuScreen(navHostController: NavHostController, viewModel: MainViewModel){
    val context = LocalContext.current

    val backgroundColor = MaterialTheme.colorScheme.surfaceDim
    val buttonColor = MaterialTheme.colorScheme.primary
    val buttonText = MaterialTheme.colorScheme.onPrimary
    val textColor = MaterialTheme.colorScheme.onSurface

    val imageResource = painterResource(R.drawable.walking)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = imageResource,
                contentDescription = "App Image",
                modifier = Modifier.size(100.dp)
            )
            DisplayInfo(
                stringResource(id = R.string.menu_msg),
                fontSize = 45.sp,
                lineHeight = 50.sp,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
            Image(
                painter = imageResource,
                contentDescription = "App Image",
                modifier = Modifier.size(100.dp)
            )
        }
        SimpleButton(
            onButtonClick = {
                navHostController.navigate("Preferences")
            },
            name = stringResource(id = R.string.preferences),
            color = buttonColor,
            textColor = buttonText)
        SimpleButton(
            onButtonClick = {
                navHostController.navigate("Collect")
            },
            name = stringResource(id = R.string.collect_data),
            color = buttonColor,
            textColor = buttonText)
        SimpleButton(
            onButtonClick = {
                navHostController.navigate("Collect")
            },
            name = stringResource(id = R.string.test),
            color = buttonColor,
            textColor = buttonText)
        SimpleButton(
            onButtonClick = {
                navHostController.navigate("Model")
            },
            name = "Model",
            color = buttonColor,
            textColor = buttonText)
    }
}