package com.example.inzynierka1.viewmodels

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.inzynierka1.FileManager
import com.example.inzynierka1.Sensors
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
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sensors: Sensors,
    private val fileManager: FileManager
) : ViewModel() {

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

    private val TAG = "Main_Activity_ViewModel"


    fun onCreate() {
        sensors.setUpSensorStuff()
        sensors.setMainViewModel(this)
        Log.d(TAG, "Utworzono")
    }


    private fun mToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    private fun write(values: List<String>) {
        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val name = fileManager.getName()
        Log.d(TAG, name)
        val file = File(directory, name)
        file.createNewFile()

        // Write values to the file
        file.printWriter().use { out ->
            values.forEach { value ->
                out.println(value)
            }
        }

        Log.d(TAG, "Zapisano plik")
    }

    //    private var sensorValues = mutableListOf<String>()
    private var isCollectingData = false

//    fun writeSensors() {
////        sensors.nullValues()
//        val sensorValues = sensors.getValues()
////        updateSensorMessage("Kliknięto przycisk")
//        updateMessage("Zapisywanie danych")
//        write(sensorValues)
//    }

    fun writeSensors() {
        if (!isCollectingData) {
            isCollectingData = true
            sensors.nullValues()
            Log.d(TAG, "Rozpoczęto zbieranie danych")
            updateMessage("Trwa zbieranie danych")
            updateButton("Trwa zbieranie danych")
            updateUserState(UserState.WALKING)
            CoroutineScope(Dispatchers.Default).launch {
                delay(5000)
                withContext(Dispatchers.Main) {
                    isCollectingData = false
                    val sensorValues = sensors.getValues()
                    Log.d(TAG, "Zakończono zbieranie danych")
                    updateMessage("Zakończono zbieranie danych")
                    updateButton("Rozpocznij ponownie")
                    updateUserState(UserState.STANDING)
                    write(sensorValues)
                }
            }
        } else {
            updateMessage("Dane są zbierane")
        }
    }
}

