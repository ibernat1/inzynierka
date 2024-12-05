package com.example.inzynierka1

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.inzynierka1.viewmodels.MainViewModel
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0

        // Tworzymy mockowane zależności
        val sensorsManager = mock<SensorsManager>()
        val fileManager = mock<FileManager>()
        val fileWriter = mock<FileWriter>()
        val fileReader = mock<FileReader>()
        val modelManager = mock<ModelManager>()
        val httpClient = mock<HttpClient>()
        val userPreferencesRepository = mock<UserPreferencesRepository>()

        // Inicjalizujemy ViewModel
        mainViewModel = MainViewModel(
            sensorsManager = sensorsManager,
            fileManager = fileManager,
            fileWriter = fileWriter,
            fileReader = fileReader,
            modelManager = modelManager,
            httpClient = httpClient,
            userPreferencesRepository = userPreferencesRepository
        )
    }

    @Test
    fun `isTimeValid should return true when collectingTimeString is a valid number greater than zero`() {
        // Ustawiamy wartość collectingTimeString tylko w teście
        mainViewModel.setCollectingTimeString("10")

        // Testujemy
        val result = mainViewModel.isTimeValid()

        assertTrue(result) // Oczekujemy, że wynik będzie true
    }

    @Test
    fun `isTimeValid should return false when collectingTimeString is zero`() {
        // Ustawiamy wartość collectingTimeString
        mainViewModel.setCollectingTimeString("0")

        // Testujemy
        val result = mainViewModel.isTimeValid()

        assertFalse(result) // Oczekujemy, że wynik będzie false
    }
}