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

/** Single place that constructs the Ktor [HttpClient]; injected as a singleton by Koin. */
object HttpClientFactory {

    val json: Json = Json {
        ignoreUnknownKeys = true      // TMDB returns many fields we don't model
        explicitNulls = false
        coerceInputValues = true
        isLenient = true
    }

    fun create(
        baseUrl: String = NetworkConstants.BASE_URL,
        accessToken: String = NetworkConstants.ACCESS_TOKEN,
        enableLogging: Boolean = true,
    ): HttpClient = HttpClient(OkHttp) {
        // Loud, early diagnostic: without a token EVERY request 401s ("couldn't load movies").
        if (accessToken.isBlank()) {
            Log.e(
                AndroidHttpLogger.TAG,
                "TMDB_ACCESS_TOKEN is empty — all requests will fail with 401 Unauthorized. " +
                    "Add a v4 API Read Access Token to local.properties and rebuild. " +
                    "See https://www.themoviedb.org/settings/api",
            )
        }

        expectSuccess = true          // non-2xx throws ClientRequestException/ServerResponseException

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
                logger = AndroidHttpLogger     // -> Logcat, filterable by the "TMDB-HTTP" tag
                level = LogLevel.ALL           // method, URL, headers AND request/response bodies
                // Never print the bearer token to Logcat.
                sanitizeHeader { header -> header == HttpHeaders.Authorization }
            }
        }

        // TMDB v4 bearer auth.
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
