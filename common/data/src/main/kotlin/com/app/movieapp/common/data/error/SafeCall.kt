package com.app.movieapp.common.data.error

import com.app.movieapp.common.domain.error.AppError
import com.app.movieapp.common.domain.result.Outcome
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import java.io.IOException
import java.net.UnknownHostException
import kotlinx.coroutines.CancellationException

/**
 * The ONE place network exceptions become typed [AppError]s. Nothing above the data layer ever
 * sees a raw exception. Coroutine cancellation is always rethrown.
 */
suspend inline fun <T> safeApiCall(crossinline block: suspend () -> T): Outcome<T> = try {
    Outcome.Success(block())
} catch (e: CancellationException) {
    throw e
} catch (e: Throwable) {
    Outcome.Failure(e.toAppError())
}

/** Same guarantee for local DB operations. */
suspend inline fun <T> safeDbCall(crossinline block: suspend () -> T): Outcome<T> = try {
    Outcome.Success(block())
} catch (e: CancellationException) {
    throw e
} catch (e: Throwable) {
    Outcome.Failure(AppError.Database)
}

fun Throwable.toAppError(): AppError = when (this) {
    is UnknownHostException, is IOException -> AppError.Network
    is HttpRequestTimeoutException -> AppError.Timeout
    is ClientRequestException -> when (response.status.value) {
        401, 403 -> AppError.Unauthorized
        404 -> AppError.NotFound
        else -> AppError.Http(response.status.value)
    }
    is ServerResponseException -> AppError.Http(response.status.value)
    else -> AppError.Unknown(this)
}
