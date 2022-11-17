package org.spray.cc.network

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.spray.cc.utils.await
import java.util.concurrent.TimeUnit

object NetworkHelper {

    private lateinit var okHttpClient: OkHttpClient

    fun okHttpClient(): OkHttpClient {
        return okHttpClient
    }

    fun init() {
        okHttpClient = OkHttpClient().newBuilder().apply {
            connectTimeout(20, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(20, TimeUnit.SECONDS)
        }.build()
    }

    suspend fun parseContent(url: String): String? {
        val request = Request.Builder().url(url)

        var response: Response? = null
        try {
            response = okHttpClient().newCall(request.build()).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return response?.body?.string()
    }

}