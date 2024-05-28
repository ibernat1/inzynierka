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

class FileManager @Inject constructor(@ApplicationContext private val context: Context){
    val TAG = "FILE MANAGER"

    @RequiresApi(Build.VERSION_CODES.O)
    fun getName(name: String): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val formatted = current.format(formatter)
        return "$name$formatted.txt"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createFile(name: String): File {
        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val name = getName(name)
        Log.d(TAG, name)
        val file = File(directory, name)
        file.createNewFile()
        return file
    }

}