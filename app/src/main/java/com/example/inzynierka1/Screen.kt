package com.example.inzynierka1

sealed class Screen (val route: String){
    object PreferencesScreen : Screen("preferences_screen")
    object MainScreen : Screen("main_screen")
    object TestScreen : Screen("test_screen")
}