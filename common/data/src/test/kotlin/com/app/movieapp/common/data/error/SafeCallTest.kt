package com.app.movieapp.common.data.error

import com.app.movieapp.common.domain.error.AppError
import com.app.movieapp.common.domain.result.Outcome
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import java.io.IOException
import java.net.UnknownHostException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SafeCallTest {
    private fun clientReturning(status: HttpStatusCode): HttpClient =
        HttpClient(MockEngine) {
            expectSuccess = true
            engine { addHandler { respond("", status) } }
        }

    private suspend fun mapStatus(status: HttpStatusCode): AppError {
        val outcome = clientReturning(status).use { client ->
            safeApiCall { client.get("/") }
        }
        return (outcome as Outcome.Failure).error
    }

    @Test
    fun `unknown host maps to Network`() {
        assertEquals(AppError.Network, UnknownHostException().toAppError())
    }

    @Test
    fun `io exception maps to Network`() {
        assertEquals(AppError.Network, IOException("socket closed").toAppError())
    }

    @Test
    fun `request timeout maps to Timeout and is not swallowed by the IOException branch`() {
        val timeout = HttpRequestTimeoutException("https://api.themoviedb.org/3/movie/popular", 30_000)
        assertEquals(AppError.Timeout, timeout.toAppError())
    }

    @Test
    fun `unrecognised throwable maps to Unknown and keeps the cause`() {
        val boom = IllegalStateException("boom")
        val mapped = boom.toAppError()
        assertTrue(mapped is AppError.Unknown)
        assertSame(boom, (mapped as AppError.Unknown).cause)
    }

    @Test
    fun `401 maps to Unauthorized`() = runBlocking {
        assertEquals(AppError.Unauthorized, mapStatus(HttpStatusCode.Unauthorized))
    }

    @Test
    fun `403 maps to Unauthorized`() = runBlocking {
        assertEquals(AppError.Unauthorized, mapStatus(HttpStatusCode.Forbidden))
    }

    @Test
    fun `404 maps to NotFound`() = runBlocking {
        assertEquals(AppError.NotFound, mapStatus(HttpStatusCode.NotFound))
    }

    @Test
    fun `other 4xx maps to Http with the status code`() = runBlocking {
        assertEquals(AppError.Http(418), mapStatus(HttpStatusCode(418, "I'm a teapot")))
    }

    @Test
    fun `5xx maps to Http with the status code`() = runBlocking {
        assertEquals(AppError.Http(503), mapStatus(HttpStatusCode.ServiceUnavailable))
    }

    @Test
    fun `safeApiCall returns Success when the block succeeds`() = runBlocking {
        val outcome = safeApiCall { 42 }
        assertEquals(Outcome.Success(42), outcome)
    }

    @Test
    fun `safeApiCall rethrows cancellation rather than swallowing it`() {
        assertThrows(CancellationException::class.java) {
            runBlocking { safeApiCall<Unit> { throw CancellationException("cancelled") } }
        }
    }

    @Test
    fun `safeDbCall maps any failure to Database`() = runBlocking {
        val outcome = safeDbCall<Unit> { throw IllegalStateException("disk full") }
        assertEquals(Outcome.Failure(AppError.Database), outcome)
    }

    @Test
    fun `safeDbCall rethrows cancellation rather than swallowing it`() {
        assertThrows(CancellationException::class.java) {
            runBlocking { safeDbCall<Unit> { throw CancellationException("cancelled") } }
        }
    }

    @Test
    fun `safeDbCall returns Success when the block succeeds`() = runBlocking {
        assertEquals(Outcome.Success("ok"), safeDbCall { "ok" })
    }
}
