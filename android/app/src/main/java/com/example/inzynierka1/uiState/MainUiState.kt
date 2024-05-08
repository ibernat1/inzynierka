package com.example.inzynierka1.uiState

data class MainUiState (
    val message: String = "Aby rozpocząć kliknij przycisk na dole ekranu",
    val button: String = "Rozpocznij",
    val userState: UserState = UserState.STANDING
)

enum class UserState {
    STANDING,
    WALKING
}