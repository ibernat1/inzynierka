package com.example.inzynierka1

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

    @RunWith(AndroidJUnit4::class)
    class FileManagerInstrumentedTest {

        @Test
        fun createFileShouldCreateANewFile() {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val fileManager = FileManager(context)

            val fileName = "testFile.txt"
            val file = fileManager.createFile(fileName)

            // Sprawdzenie czy plik faktycznie istnieje
            assertNotNull(file)
            assertTrue(file.exists())

            // Czyszczenie testu
            file.delete()
        }
    }