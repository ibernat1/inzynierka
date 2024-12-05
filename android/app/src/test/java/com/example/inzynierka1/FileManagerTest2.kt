package com.example.inzynierka1

import android.content.Context
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mockStatic
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FileManagerTest2 {

    @Mock
    lateinit var context: Context

    private lateinit var fileManager: FileManager

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        fileManager = FileManager(context)
    }

    @Test
    fun `test getFileName should return correct filename with timestamp`() {
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
            Assertions.assertEquals("testFile$formattedDate.txt", result)
        }
    }
}
