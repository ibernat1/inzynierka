package com.example.inzynierka1

import android.content.Context
import android.os.Environment
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.mockStatic
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FileManagerTest2 {

    @Mock
    lateinit var context: Context

    private lateinit var fileManager: FileManager

    @Mock
    lateinit var mockFile: File

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        fileManager = FileManager(context)
    }

    @Test
    fun `test getFileName should return correct filename with timestamp`() {
        // Mockowanie bieżącej daty
        val date = LocalDateTime.of(2024, 12, 5, 14, 30, 0, 0) // 2024-12-05 14:30:00
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val formattedDate = date.format(formatter)

        // Mockowanie LocalDateTime.now() przy użyciu mockStatic
        mockStatic(LocalDateTime::class.java).use { mock ->
            whenever(LocalDateTime.now()).thenReturn(date)

            // Testowanie metody
            val result = fileManager.getFileName("testFile")

            // Sprawdzenie, czy wynik jest zgodny z oczekiwaniem
            assertEquals("testFile$formattedDate.txt", result)
        }
    }
    @Test
    fun `test createFile should create a new file`() {
        // Użycie Robolectric do emulacji ścieżki
        val context = ApplicationProvider.getApplicationContext<Context>()
        val fileManager = FileManager(context)

        val fileName = "testFile.txt"
        val file = fileManager.createFile(fileName)

        // Sprawdzenie, czy plik został utworzony
        assertNotNull(file)
        assertTrue(file.exists())

        // Usunięcie pliku
        file.delete()
    }

//    @Config(sdk = [Config.OLDEST_SDK])
//    @Test
//    fun `test createFile should create a new file`() {
//        // Mockowanie Environment.getExternalStoragePublicDirectory za pomocą Robolectric
//        val mockDirectory = File("/mock/directory")
//        Shadows.shadowOf(Environment::class.java).setExternalStoragePublicDirectory(mockDirectory)
//
//        // Mockowanie pliku
//        val fileName = "testFile.txt"
//        val filePath = File(mockDirectory, fileName)
//
//        // Mockowanie metody createNewFile(), aby zawsze zwracała true
//        whenever(mockFile.createNewFile()).thenReturn(true)
//
//        // Testowanie metody createFile
//        val result = fileManager.createFile(fileName)
//
//        // Sprawdzenie, czy metoda createNewFile została wywołana
//        verify(mockFile).createNewFile()
//
//        // Sprawdzenie, czy wynik jest poprawnym obiektem File
//        assertTrue(result.exists())
//    }

}
