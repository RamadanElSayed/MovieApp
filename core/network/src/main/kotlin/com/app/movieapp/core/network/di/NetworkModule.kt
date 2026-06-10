package com.app.movieapp.core.network.di

import com.app.movieapp.core.network.BuildConfig
import com.app.movieapp.core.network.HttpClientFactory
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single<Json> { HttpClientFactory.json }

    single<HttpClient> { HttpClientFactory.create(enableLogging = BuildConfig.DEBUG) }
}
