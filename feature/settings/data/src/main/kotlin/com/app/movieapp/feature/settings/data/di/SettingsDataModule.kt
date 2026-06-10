package com.app.movieapp.feature.settings.data.di

import com.app.movieapp.core.contract.preferences.UserPreferencesRepository
import com.app.movieapp.feature.settings.data.DataStoreUserPreferencesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val settingsDataModule = module {
    single<UserPreferencesRepository> { DataStoreUserPreferencesRepository(androidContext()) }
}
