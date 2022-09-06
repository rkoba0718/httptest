package com.example.httptest

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

object HttpClient {
    //OKHttp3はシングルトンで使う
    val instance = OkHttpClient()
}

class HttpUtil {

    private val JSON_MEDIA = "application/json; charset=utf-8".toMediaType()

    fun httpGet(url: String): String? {
        val getRequest = Request.Builder()
            .url(url)
            .build()

        val response = HttpClient.instance.newCall(getRequest).execute()
        return response.body?.string()
    }

    fun httpPost(url: String): String? {
        val sendDataJson = "{\"id\":\"1234567890\",\"name\":\"hogehoge\"}"

        val postRequest = Request.Builder()
            .url(url)
            .post(sendDataJson.toRequestBody(JSON_MEDIA))
            .build()

        val response = HttpClient.instance.newCall(postRequest).execute()
        return response.body?.string()
    }
}
