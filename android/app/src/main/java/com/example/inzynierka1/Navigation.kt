package com.example.inzynierka1

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.inzynierka1.ui.screens.MainActivityScreen
import com.example.inzynierka1.ui.screens.PreferencesScreen
@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Preferences"){
        composable("Preferences"){
            PreferencesScreen(navController)
        }
        composable("Main"){
            MainActivityScreen()
        }
    }

}
