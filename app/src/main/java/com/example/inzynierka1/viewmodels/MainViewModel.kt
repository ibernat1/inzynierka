package com.example.inzynierka1.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.inzynierka1.FileManager
import com.example.inzynierka1.SensorsManager
import com.example.inzynierka1.uiState.MainUiState
import com.example.inzynierka1.uiState.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sensorsManager: SensorsManager,
    private val fileManager: FileManager
) : ViewModel() {

    private val TAG = "Main_Activity_ViewModel"

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private fun updateMessage(text: String) {
        _uiState.update { currentState ->
            currentState.copy(
                message = text
            )
        }
    }

    private fun updateButton(text: String) {
        _uiState.update { currentState ->
            currentState.copy(
                button = text
            )
        }
    }

    private fun updateUserState(userState: UserState){
        _uiState.update { currentState ->
            currentState.copy(userState = userState)
        }
    }

    init {
        _uiState.value = MainUiState()
        onCreate()
    }

    private fun onCreate() {
        sensorsManager.setUpSensorStuff()
        sensorsManager.setMainViewModel(this)
        Log.d(TAG, "Utworzono")
    }

    private var isCollectingData = false

    @RequiresApi(Build.VERSION_CODES.O)
    fun writeSensors() {
        if (!isCollectingData) {
            isCollectingData = true
            sensorsManager.nullValues()
            Log.d(TAG, "Rozpoczęto zbieranie danych")
            updateMessage("Trwa zbieranie danych")
            updateButton("Trwa zbieranie danych")
            updateUserState(UserState.WALKING)
            CoroutineScope(Dispatchers.Default).launch {
                delay(5000)
                withContext(Dispatchers.Main) {
                    isCollectingData = false
                    val sensorValues = sensorsManager.getValues()
                    Log.d(TAG, "Zakończono zbieranie danych")
                    updateMessage("Zakończono zbieranie danych")
                    updateButton("Rozpocznij ponownie")
                    updateUserState(UserState.STANDING)
                        fileManager.write(sensorValues)
                }
            }
        } else {
            updateMessage("Dane są zbierane")
        }
    }
}