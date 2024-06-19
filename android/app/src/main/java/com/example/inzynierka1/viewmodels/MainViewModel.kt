package com.example.inzynierka1.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inzynierka1.FileManager
import com.example.inzynierka1.FileWriter
import com.example.inzynierka1.SensorsManager
import com.example.inzynierka1.UserPreferencesRepository
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
open class MainViewModel @Inject constructor(
    private val sensorsManager: SensorsManager,
    private val fileManager: FileManager,
    private val fileWriter: FileWriter,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val TAG = "Main_Activity_ViewModel"

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    val userName: MutableState<String> = mutableStateOf("")
    val collectingTimeString: MutableState<String> = mutableStateOf("")
    var isInputValid: MutableState<Boolean> =  mutableStateOf(false)

    private var collectingTime: Long = 10000
    val progress: MutableState<Float> = mutableFloatStateOf(0F)


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
        sensorsManager.setUpSensors()
        sensorsManager.setMainViewModel(this)
        Log.d(TAG, "Utworzono")
    }

    suspend fun savePreferences(){
        userPreferencesRepository.updateUserName(userName.toString())
        userPreferencesRepository.updateCollectingTime(collectingTimeString.toString())
    }

    private var isCollectingData = false

    @RequiresApi(Build.VERSION_CODES.O)
    fun writeSensors() {
        if (!isCollectingData) {
            isCollectingData = true
            sensorsManager.nullValues()
            Log.d(TAG, "Rozpoczęto zbieranie danych")
            collectingTime = collectingTimeString.value.toLong() * 1000
            updateUserState(UserState.WALKING)
            CoroutineScope(Dispatchers.Default).launch {
//                delay(collectingTime)
                repeat(100) { // repeat 100 times to make 5 seconds
                    delay(collectingTime / 100)
                    progress.value += 0.01f
                }
                withContext(Dispatchers.Main) {
                    isCollectingData = false
                    val sensorValues = sensorsManager.getValues()
                    Log.d(TAG, "Zakończono zbieranie danych")
                    updateUserState(UserState.COLLECTED_DATA)
                    fileWriter.writeToFile(fileManager.createFile(userName.value),sensorValues)
                }
            }
        }
    }
}