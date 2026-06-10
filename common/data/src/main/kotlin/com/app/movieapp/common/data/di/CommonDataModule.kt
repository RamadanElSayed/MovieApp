package com.app.movieapp.common.data.di

import com.app.movieapp.common.data.connectivity.AndroidConnectivityObserver
import com.app.movieapp.common.domain.connectivity.ConnectivityObserver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val commonDataModule = module {
    single<ConnectivityObserver> { AndroidConnectivityObserver(androidContext()) }
}
