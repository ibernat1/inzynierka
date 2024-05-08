package com.example.inzynierka1.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TestScreen() {
    val backgroundColor = Color(0xFFD8BDFF)
    val buttonColor = Color(0xFF552599)
    val buttonText = Color(0xFFEFE4FF)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = "Drugi ekran", color = buttonColor
        )
    }
}