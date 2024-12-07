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
import com.example.inzynierka1.ui.components.playSoundWithSoundPool
import com.example.inzynierka1.viewmodels.MainViewModel

@Composable
fun ModelScreen(viewModel: MainViewModel){
    val context = LocalContext.current

    val backgroundColor = MaterialTheme.colorScheme.surfaceDim
    val buttonColor = MaterialTheme.colorScheme.primary
    val buttonText = MaterialTheme.colorScheme.onPrimary
    val textColor = MaterialTheme.colorScheme.onSurface


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        SimpleButton(
            onButtonClick = {
                viewModel.getDataFromFile()
            },
            name = "Pobierz dane",
            color = buttonColor,
            textColor = buttonText)
        SimpleButton(
            onButtonClick = {
                viewModel.uploadDataToServer()
            },
            name = "Wyslij dane",
            color = buttonColor,
            textColor = buttonText)
        DisplayInfo(
            viewModel.inferenceScore.value.toString(),
            fontSize = 25.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
        SimpleButton(
            onButtonClick = {
                viewModel.getModel()
            },
            name = "pobierz model",
            color = buttonColor,
            textColor = buttonText)
    }

}