package com.example.inzynierka1

import android.content.Context
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.io.File
import java.io.BufferedReader

class FileReaderTest {

    private val context: Context = mock()
    private val fileReader: FileReader = FileReader(context)

    @Test
    fun `test readFromFile should parse floats correctly`() {
        // Mockujemy plik i BufferedReader
        val file: File = mock()
        val bufferedReader: BufferedReader = mock()

        // Przygotowanie danych, które BufferedReader będzie zwracał
        val line1 = "1.0,2.0,3.0"
        val line2 = "4.0,5.0,6.0"

        // Mockowanie zachowań
        whenever(file.bufferedReader()).thenReturn(bufferedReader)
        whenever(bufferedReader.readLine()).thenReturn(line1, line2, null) // Przypisanie dwóch wierszy i null na końcu

        // Wywołanie testowanej metody
        val result = fileReader.readFromFile(file)

        // Weryfikacja
        val expected = listOf(
            listOf(1.0f, 2.0f, 3.0f),
            listOf(4.0f, 5.0f, 6.0f)
        )
        Assertions.assertEquals(expected, result)
    }
}