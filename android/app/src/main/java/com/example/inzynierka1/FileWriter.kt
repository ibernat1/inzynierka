package com.example.inzynierka1

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class FileWriter @Inject constructor(@ApplicationContext private val context: Context) {
    fun writeToFile(file: File, content: List<String>){
        file.printWriter().use { out ->
            content.forEach { line ->
                out.println(line)
            }
        }
    }
}