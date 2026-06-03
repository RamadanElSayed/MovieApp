package com.app.movieapp.core.network.di

import com.app.movieapp.core.network.BuildConfig
import com.app.movieapp.core.network.HttpClientFactory
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.koin.dsl.module

/** Koin module exposing the shared [HttpClient] + [Json]. Loaded once at app startup. */
val networkModule = module {
    single<Json> { HttpClientFactory.json }
    // Verbose HTTP logging only in debug builds; release stays quiet.
    single<HttpClient> { HttpClientFactory.create(enableLogging = BuildConfig.DEBUG) }
}
