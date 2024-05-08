package com.example.inzynierka1

import android.content.Context
import android.os.Environment
import com.example.inzynierka1.FileManager
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File

class FileManagerTest {

    private lateinit var fileManager: FileManager
    private lateinit var context: Context

    @Before
    fun setup() {
//        context = InstrumentationRegistry.getInstrumentation().targetContext
        fileManager = FileManager(context)
    }

    @Test
    fun testNameGeneration() {
        val name = fileManager.getName()
        val regex = Regex("\\d{14}\\.txt")
        assert(regex.matches(name))
    }

    @Test
    fun testFileWriting() {
        val values = listOf("Value 1", "Value 2", "Value 3")
        fileManager.write(values)

        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val name = fileManager.getName()
        val file = File(directory, name)

        assertEquals(true, file.exists())

        // Cleanup
        file.delete()
    }
}
