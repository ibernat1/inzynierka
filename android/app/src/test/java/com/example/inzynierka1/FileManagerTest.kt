package com.example.inzynierka1

import android.content.Context
import android.os.Environment
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.mockStatic
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FileManagerTest {

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var mockFile: File

    private lateinit var fileManager: FileManager

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        fileManager = FileManager(context)
    }

    @Test
    fun test_getFileName_should_return_correct_filename_with_timestamp() {
        // Mockowanie bieżącej daty
        val mockDate = LocalDateTime.of(2024, 12, 5, 14, 30, 0, 0) // 2024-12-05 14:30:00
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val formattedDate = mockDate.format(formatter)

        // Mockowanie LocalDateTime.now() przy użyciu mockStatic
        mockStatic(LocalDateTime::class.java).use { mock ->
            whenever(LocalDateTime.now()).thenReturn(mockDate)

            // Testowanie metody
            val result = fileManager.getFileName("testFile")

            // Sprawdzenie, czy wynik jest zgodny z oczekiwaniem
            assertEquals("testFile$formattedDate.txt", result)
        }
    }

    @Test
    fun `test createFile should create a new file`() {
        // Mockowanie Environment.getExternalStoragePublicDirectory
        val mockDirectory = File("/mock/directory")
        whenever(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)).thenReturn(mockDirectory)

        // Mockowanie konstrukcji pliku
        val fileName = "testFile.txt"
        val filePath = File(mockDirectory, fileName)

        // Mockowanie metody createNewFile(), aby zawsze zwracała true (plik został utworzony)
        whenever(mockFile.createNewFile()).thenReturn(true)

        // Testowanie metody createFile
        val result = fileManager.createFile(fileName)

        // Sprawdzenie, czy metoda createNewFile została wywołana
        verify(mockFile).createNewFile()

        // Sprawdzenie, czy wynik jest poprawnym obiektem File
        assertTrue(result.exists())
    }
}
