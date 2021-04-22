package com.example.myapplication.api.client

import jp.team.eworks.e_core_library.api.BaseApiClient
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object SampleApiClient: BaseApiClient() {
    override val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().apply {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            callTimeout(30, TimeUnit.SECONDS)

            retryOnConnectionFailure(false)
        }.build()
    }
}
