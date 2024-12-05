package com.example.inzynierka1

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class HttpClient @Inject constructor(@ApplicationContext private val context: Context) {
    fun uploadFileToServer(file: File, serverUrl: String): String? {
        // Sprawdź, czy plik istnieje
        if (!file.exists() || !file.isFile) {
            throw IllegalArgumentException("Invalid file provided.")
        }

        // Klient HTTP
        val client = OkHttpClient()

        // Utwórz część wieloczęściową dla pliku
        val fileBody = file.asRequestBody("text/plain".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", file.name, fileBody)
            .build()

        // Utwórz żądanie HTTP POST
        val request = Request.Builder()
            .url(serverUrl)
            .post(multipartBody)
            .build()

        return try {
            // Wykonaj żądanie
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                }
                // Zwróć odpowiedź serwera
                response.body?.string()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

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