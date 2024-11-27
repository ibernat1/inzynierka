package com.example.inzynierka1

import android.content.Context
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FileManager @Inject constructor(@ApplicationContext private val context: Context) {
    val TAG = "FILE MANAGER"

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFileName(name: String): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val formatted = current.format(formatter)
        return "$name$formatted.txt"
    }

//    companion object {
//        fun getFileName(name: String): String {
//            val current = LocalDateTime.now()
//            val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
//            val formatted = current.format(formatter)
//            return "$name$formatted.txt"
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createFile(name: String): File {
        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        Log.d(TAG, name)
        val file = File(directory, name)
        file.createNewFile()
        return file
    }

    fun getFile(name: String): File {
        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        return File(directory, name)
    }
}