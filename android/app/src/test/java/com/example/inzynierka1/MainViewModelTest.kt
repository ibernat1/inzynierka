package com.example.inzynierka1

import android.util.Log
import com.example.inzynierka1.viewmodels.MainViewModel
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var sensorsManager: SensorsManager
    private lateinit var fileManager: FileManager
    private lateinit var fileWriter: FileWriter

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0

        sensorsManager = mock()
        fileManager = mock()
        fileWriter = mock()

        val fileReader = mock<FileReader>()
        val modelManager = mock<ModelManager>()
        val httpClient = mock<HttpClient>()
        val userPreferencesRepository = mock<UserPreferencesRepository>()

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


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `writeSensors should start and complete the data collection process`() = runTest {
        // Przygotowanie testu
        val collectingTimeString = "5" // 5 seconds
        mainViewModel.setCollectingTimeString(collectingTimeString)

        every { sensorsManager.nullValues() } just Runs
        every { sensorsManager.getValuesList() } returns listOf("sensor_data_1", "sensor_data_2")
        every { fileManager.createFile(any()) } returns mockk()
        every { fileWriter.writeToFile(any(), any()) } just Runs
        every { fileManager.getFileName(any()) } returns "test_file.txt"

        // Wywołujemy metodę writeSensors
        mainViewModel.writeSensors()

        // Używamy testu, żeby poczekać na zakończenie asynchronicznych operacji
        advanceUntilIdle()

        // Sprawdzamy czy zmienne i metody zostały wywołane w odpowiedniej kolejności
        verifyOrder {
            sensorsManager.nullValues() // Czy zostały zainicjowane wartości?
            sensorsManager.getValuesList() // Czy dane zostały pobrane?
            fileManager.getFileName(any()) // Czy plik został wygenerowany?
            fileWriter.writeToFile(any(), any()) // Czy dane zostały zapisane do pliku?
        }

        // Sprawdzamy czy stany zostały zmienione
        assertTrue(mainViewModel.isCollectingData)
        assertFalse(mainViewModel.isCollectingData) // Po zakończeniu asynchronicznego zbierania danych
    }

    @Test
    fun `writeSensors should not start if data is already being collected`() = runBlocking {
        mainViewModel.isCollectingData = true

        every { sensorsManager.nullValues() } just Runs

        mainViewModel.writeSensors()

        verify(exactly = 0) { sensorsManager.nullValues() }
    }
}