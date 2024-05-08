package com.example.inzynierka1.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.inzynierka1.ui.components.SimpleButton

@Composable
fun PreferencesScreen(navHostController: NavHostController) {
    val backgroundColor = Color(0xFFD8BDFF)
    val buttonColor = Color(0xFF552599)
    val buttonText = Color(0xFFD8BDFF)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
            .background(color = backgroundColor),
        verticalArrangement = Arrangement.Center
    ){
        SimpleButton({ navHostController.navigate("Main") }, name = "Dalej", buttonColor, buttonText)
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreferencesScreenPreview(){
//    val navController = rememberNavController()
//    PreferencesScreen(navController = navController)
//}