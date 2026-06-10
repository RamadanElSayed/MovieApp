package com.app.movieapp.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import android.util.Log
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    val json: Json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        coerceInputValues = true
        isLenient = true
    }

    fun create(
        baseUrl: String = NetworkConstants.BASE_URL,
        accessToken: String = NetworkConstants.ACCESS_TOKEN,
        enableLogging: Boolean = true,
    ): HttpClient = HttpClient(OkHttp) {
        if (accessToken.isBlank()) {
            Log.e(
                AndroidHttpLogger.TAG,
                "TMDB_ACCESS_TOKEN is empty — all requests will fail with 401 Unauthorized. " +
                    "Add a v4 API Read Access Token to local.properties and rebuild. " +
                    "See https://www.themoviedb.org/settings/api",
            )
        }

        expectSuccess = true

        install(ContentNegotiation) { json(json) }

        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 15_000
            socketTimeoutMillis = 30_000
        }

        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = 2)
            exponentialDelay()
        }

        if (enableLogging) {
            install(Logging) {
                logger = AndroidHttpLogger
                level = LogLevel.ALL

                sanitizeHeader { header -> header == HttpHeaders.Authorization }
            }
        }

        install(Auth) {
            bearer { loadTokens { BearerTokens(accessToken, refreshToken = "") } }
        }

        defaultRequest {
            url(baseUrl)
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Accept, ContentType.Application.Json.toString())
        }
    }
}
