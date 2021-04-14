package jp.team.eworks.e_core_library.api

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

abstract class BaseApiClient {
    abstract val httpClient: OkHttpClient

    suspend fun call(request: Request): Response {
        @Suppress("BlockingMethodInNonBlockingContext")
        return httpClient.newCall(request).execute()
    }
}
