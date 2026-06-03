package com.app.movieapp.core.network

import android.util.Log
import io.ktor.client.plugins.logging.Logger

/**
 * Routes Ktor's HTTP logs to Logcat under a single, easy-to-filter tag.
 *
 * The default Ktor logger writes via `println`, which on Android lands under the noisy `System.out`
 * tag and is easy to miss. This sends every line to [Log] with [TAG] so you can simply filter
 * Logcat by `TMDB-HTTP` to watch every request, header and response body.
 *
 * Logcat truncates lines at ~4 KB, so long JSON bodies are chunked to stay readable.
 */
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
