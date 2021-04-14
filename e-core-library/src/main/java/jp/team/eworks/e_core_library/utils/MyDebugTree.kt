package jp.team.eworks.e_core_library.utils

import android.util.Log
import timber.log.Timber

class MyDebugTree : Timber.DebugTree() {
    companion object {
        private const val MAX_LENGTH = 3000
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val thread = Throwable().stackTrace

        if (thread.size >= 5) {
            val stack = thread[5]
            val fileName = stack.fileName

            val linkMessage = "($fileName:${stack.lineNumber})"
            output(priority, tag, message, linkMessage)
        } else {
            output(priority, tag, message)
        }
    }

    // ログ内容が長い場合は分割して出力する
    private fun output(priority: Int, tag: String?, message: String, linkMessage: String? = null) {
        if (message.length + (linkMessage?.length ?: 0) <= MAX_LENGTH) {
            val msg = if (linkMessage != null) {
                message + linkMessage
            } else {
                message
            }
            Log.println(priority, tag, msg)
            return
        }

        val max = (message.length / MAX_LENGTH) + if (message.length % MAX_LENGTH == 0) 0 else 1
        for (i in 0 until max) {
            val startPosition = i * MAX_LENGTH
            val endPosition: Int? = if (message.length > startPosition + MAX_LENGTH) {
                startPosition + MAX_LENGTH
            } else {
                null
            }
            endPosition?.also { end ->
                Log.println(priority, tag, message.substring(startPosition, end))
            } ?: run {
                Log.println(priority, tag, message.substring(startPosition))
            }
        }
        linkMessage?.let {
            Log.println(priority, tag, it)
        }
    }
}
