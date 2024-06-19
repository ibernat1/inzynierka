package com.example.inzynierka1

import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FileManagerTest {

    @Test
    fun testGetName() {
        // Arrange
        val name = "testName"
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")

        // Act
        val result = FileManager.getFileName(name)

        // Assert
        val currentTimestamp = LocalDateTime.now().format(formatter)
        val expectedStart = "$name$currentTimestamp".substring(
            0,
            name.length + 8
        ) // Just check if the start is correct to avoid timing issues
        assertTrue(result.startsWith(expectedStart) && result.endsWith(".txt"))
    }
}