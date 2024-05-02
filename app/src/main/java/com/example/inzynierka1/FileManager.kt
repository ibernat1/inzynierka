package com.example.inzynierka1

import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class FileManager @Inject constructor(@ApplicationContext private val context: Context) {
    val TAG = "FILE MANAGER"

    @RequiresApi(Build.VERSION_CODES.O)
    fun getName(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val formatted = current.format(formatter)
        return "$formatted.txt"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun write(values: List<String>) {
        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val name = getName()
        Log.d(TAG, name)
        val file = File(directory, name)
        file.createNewFile()

        // Write values to the file
        file.printWriter().use { out ->
            values.forEach { value ->
                out.println(value)
            }
        }

        Log.d(TAG, "Zapisano plik")
    }

}