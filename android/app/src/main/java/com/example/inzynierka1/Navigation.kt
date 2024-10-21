package com.example.inzynierka1

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.inzynierka1.ui.screens.CollectScreen
import com.example.inzynierka1.ui.screens.MenuScreen
import com.example.inzynierka1.ui.screens.PreferencesScreen
import com.example.inzynierka1.viewmodels.MainViewModel

@Composable
fun Navigation() {

    val navController = rememberNavController()
    val viewModel: MainViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "Menu"){
        composable("Menu"){
            MenuScreen(navController, viewModel)
        }
        composable("Preferences"){
            PreferencesScreen(navController, viewModel)
        }
        composable("Collect"){
            CollectScreen(viewModel)
        }
    }

}
