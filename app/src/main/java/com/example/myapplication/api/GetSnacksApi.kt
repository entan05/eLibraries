package com.example.myapplication.api

import jp.team.eworks.e_core_library.api.BaseApi
import com.example.myapplication.api.client.SampleApiClient
import okhttp3.Request
import java.io.BufferedReader
import java.lang.Exception

class GetSnacksApi: BaseApi(), BaseApi.Get<String> {
    override val apiCode: Int
        get() = 100

    override val path: String
        get() = "https://sysbird.jp/toriko/api/?apikey=guest&format=json"

    override suspend fun get(): String {
        val request = Request.Builder()
            .url(path)
            .get()
            .build()

        val response = call(SampleApiClient, request)
        if (response.code != HTTP_OK) {
            throw Exception("Result Code: ${response.code}")
        }
        val sb = StringBuilder()
        BufferedReader(response.body?.charStream()).forEachLine {
            sb.append(it)
        }
        return sb.toString()
    }
}
