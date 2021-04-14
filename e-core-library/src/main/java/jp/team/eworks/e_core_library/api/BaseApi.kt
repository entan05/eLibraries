package jp.team.eworks.e_core_library.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import java.lang.Exception

abstract class BaseApi {
    companion object {
        const val HTTP_OK: Int = 200
    }

    abstract val path: String

    abstract val apiCode: Int

    interface Get<T> {
        suspend fun get(): T
    }

    interface Post<T> {
        suspend fun post(): T
    }

    interface Put<T> {
        suspend fun put(): T
    }

    protected open suspend fun call(client: BaseApiClient, request: Request) = withContext(Dispatchers.IO) {
        return@withContext try {
            client.call(request)
        } catch (e: Exception) {
            throw e
        }
    }
}
