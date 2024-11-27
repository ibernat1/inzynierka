package com.example.inzynierka1

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class FileReader @Inject constructor(@ApplicationContext private val context: Context) {
    fun readFromFile(file: File): List<List<Float>> {
        val content = mutableListOf<List<Float>>()

        file.bufferedReader().use { reader ->
            reader.forEachLine { line ->
                val lineFloats = line.split(",").mapNotNull { it.toFloatOrNull() }
                content.add(lineFloats)
            }
        }
        return content
    }
}