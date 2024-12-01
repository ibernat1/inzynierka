package com.example.inzynierka1

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class HttpClient @Inject constructor(@ApplicationContext private val context: Context) {
    suspend fun downloadTFLiteModel(url: String, fileName: String): File? {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .build()

        return withContext(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val responseBody: ResponseBody? = response.body
                if (responseBody != null) {
                    val file = File(context.filesDir, fileName)
                    FileOutputStream(file).use { outputStream ->
                        outputStream.write(responseBody.bytes())
                    }
                    file
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}