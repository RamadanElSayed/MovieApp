package com.app.movieapp.core.network

import android.util.Log
import io.ktor.client.plugins.logging.Logger

internal object AndroidHttpLogger : Logger {
    const val TAG = "TMDB-HTTP"

    private const val MAX_CHUNK = 3500

    override fun log(message: String) {
        if (message.length <= MAX_CHUNK) {
            Log.d(TAG, message)
            return
        }
        var start = 0
        while (start < message.length) {
            val end = minOf(start + MAX_CHUNK, message.length)
            Log.d(TAG, message.substring(start, end))
            start = end
        }
    }
}
