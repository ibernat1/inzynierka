package com.example.inzynierka1.viewmodels

import android.content.Context
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
import androidx.lifecycle.viewModelScope
import com.example.inzynierka1.FileManager
import com.example.inzynierka1.FileReader
import com.example.inzynierka1.FileWriter
import com.example.inzynierka1.HttpClient
import com.example.inzynierka1.ModelManager
import com.example.inzynierka1.SensorsManager
import com.example.inzynierka1.UserPreferencesRepository
import com.example.inzynierka1.uiState.MainUiState
import com.example.inzynierka1.uiState.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.TestOnly
import java.io.File
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(
    private val sensorsManager: SensorsManager,
    private val fileManager: FileManager,
    private val fileWriter: FileWriter,
    private val fileReader: FileReader,
    private val modelManager: ModelManager,
    private val httpClient: HttpClient,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val TAG = "Main_Activity_ViewModel"

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    val userName: MutableState<String> = mutableStateOf("")
    val collectingTimeString: MutableState<String> = mutableStateOf("")
    var isInputValid: MutableState<Boolean> = mutableStateOf(false)

    var collectingTime: Long = 0 //10000
    val progress: MutableState<Float> = mutableFloatStateOf(0F)

    private var isCollectingData = false

    private var fileName: String = ""

    val inferenceScore: MutableState<Float> = mutableFloatStateOf(0F)


    private fun updateUserState(userState: UserState) {
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

    fun savePreferences() {
        viewModelScope.launch {
            userPreferencesRepository.updateUserName(userName.toString())
            userPreferencesRepository.updateCollectingTime(collectingTimeString.toString())
        }
    }

    @TestOnly
    fun setCollectingTimeString(value: String) {
        collectingTimeString.value = value
    }

    fun isTimeValid(): Boolean {
        return collectingTimeString.value.toLong() * 1000 > 0
    }

    fun getDataFromFile(): List<List<Float>>{
//        val file = fileManager.getFile(fileName)
        val file = fileManager.getFile("iga20241127174532.txt")
        val content =  fileReader.readFromFile(file)
        Log.d(TAG, content.toString())
        return content
    }



    fun uploadDataToServer() {
        val file = fileManager.getFile("iga20241127174532.txt")
        CoroutineScope(Dispatchers.Main).launch {
            val response = withContext(Dispatchers.IO) {
                httpClient.uploadFileToServer(file, "http://192.168.100.102:5000/upload-data")
            }
            println("Response from server: $response")
        }
    }


    fun getModel() {
        viewModelScope.launch {
            val downloadedFile = httpClient.downloadTFLiteModel("http://192.168.100.102:5000/download-model", "modelh.tflite")

            if (downloadedFile != null && downloadedFile.exists()) {
                val score = modelManager.simpleInference(getDataFromFile())
                inferenceScore.value = score
            } else {
                println("Błąd: Model nie został pobrany.")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun writeSensors() {
        if (!isCollectingData) {
            isCollectingData = true
            progress.value = 0F
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
                    val sensorValues = sensorsManager.getValuesList()
                    Log.d(TAG, "Zakończono zbieranie danych")
                    updateUserState(UserState.COLLECTED_DATA)
                    fileName = fileManager.getFileName(userName.value)
                    fileWriter.writeToFile(fileManager.createFile(fileName), sensorValues)
                }
            }
        }
    }
}