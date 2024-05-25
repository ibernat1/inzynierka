package com.example.inzynierka1.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.inzynierka1.R
import com.example.inzynierka1.ui.components.DisplayInfo
import com.example.inzynierka1.ui.components.SimpleButton
import com.example.inzynierka1.ui.components.TextInput
import com.example.inzynierka1.viewmodels.MainViewModel

@Composable
fun PreferencesScreen(navHostController: NavHostController, viewModel: MainViewModel) {

    val backgroundColor = MaterialTheme.colorScheme.surfaceDim
    val buttonColor = MaterialTheme.colorScheme.primary
    val buttonText = MaterialTheme.colorScheme.onPrimary
    val textColor = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .padding(horizontal = 50.dp)
            .background(color = backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        DisplayInfo(
            name = "Aby przejść dalej podaj swoje imię",
            fontSize = 35.sp,
            lineHeight = 40.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
        Spacer(modifier = Modifier.weight(1f))
        TextInput(
            input = viewModel.userName,
            label = "Imię")
        Spacer(modifier = Modifier.weight(1f))
        SimpleButton(
            { navHostController.navigate("Main") },
            name = "Dalej",
            color = buttonColor,
            textColor = buttonText)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreferencesScreenPreview(){
    val navController = rememberNavController()
    val viewModel: MainViewModel = hiltViewModel()
    PreferencesScreen(navHostController = navController, viewModel = viewModel)
}