package com.app.movieapp.common.data.di

import com.app.movieapp.common.data.connectivity.AndroidConnectivityObserver
import com.app.movieapp.common.domain.connectivity.ConnectivityObserver
import com.app.movieapp.common.domain.coroutines.DefaultDispatcherProvider
import com.app.movieapp.common.domain.coroutines.DispatcherProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.bind
import org.koin.dsl.module

/** Shared data-layer singletons available to every feature's data layer. */
val commonDataModule = module {
    singleOf(::DefaultDispatcherProvider) { bind<DispatcherProvider>() }
    single<ConnectivityObserver> { AndroidConnectivityObserver(androidContext()) }
}
