package com.example.inzynierka1

import android.content.Context
import android.icu.util.Calendar
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FileManager @Inject constructor(@ApplicationContext private val context: Context) {
    fun getName(): String {
        val currentDateTime = Calendar.getInstance()
        val year = currentDateTime.get(Calendar.YEAR)
        val month = currentDateTime.get(Calendar.MONTH) + 1 // miesiące są liczone od 0 do 11
        val day = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val hour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentDateTime.get(Calendar.MINUTE)
        val second = currentDateTime.get(Calendar.SECOND)

        val name = "$year$month$day$hour$minute$second.txt"

        Log.d("FileManager",name)

        return name
//        return "nazwa.txt"
    }

}