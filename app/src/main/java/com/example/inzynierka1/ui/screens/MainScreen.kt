package com.example.inzynierka1.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.inzynierka1.ui.components.SimpleButton
import com.example.inzynierka1.ui.components.displaySensor
import com.example.inzynierka1.uiState.MainUiState
import com.example.inzynierka1.viewmodels.MainViewModel

@Composable
fun MainActivityScreen(viewModel: MainViewModel = hiltViewModel()) {
    val backgroundColor = Color(0xFFD8BDFF)
    val buttonColor = Color(0xFF552599)
    val buttonText = Color(0xFFEFE4FF)

//    val uiState = viewModel.uiState.value
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        displaySensor(uiState.message, fontSize = 25.sp)
//        uiState?.let { displaySensor(it.message ?: "", fontSize = 25.sp) }
        Spacer(modifier = Modifier.weight(1f))
        SimpleButton({ viewModel.writeSensors() } , name = "Zapisz", buttonColor, buttonText)
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun MainActivityScreenPreview(){
//    MainActivityScreen()
//}