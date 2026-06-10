package com.app.movieapp.di

import com.app.movieapp.common.data.di.commonDataModule
import com.app.movieapp.common.presentation.resource.AndroidResourceProvider
import com.app.movieapp.common.presentation.resource.ResourceProvider
import com.app.movieapp.core.database.di.databaseModule
import com.app.movieapp.core.network.di.networkModule
import com.app.movieapp.feature.favorites.data.di.favoritesDataModule
import com.app.movieapp.feature.favorites.domain.di.favoritesDomainModule
import com.app.movieapp.feature.favorites.presentation.di.favoritesPresentationModule
import com.app.movieapp.feature.moviedetails.domain.di.movieDetailsDomainModule
import com.app.movieapp.feature.moviedetails.presentation.di.movieDetailsPresentationModule
import com.app.movieapp.feature.movieslist.data.di.moviesDataModule
import com.app.movieapp.feature.movieslist.domain.di.moviesDomainModule
import com.app.movieapp.feature.movieslist.presentation.di.moviesPresentationModule
import com.app.movieapp.feature.search.data.di.searchDataModule
import com.app.movieapp.feature.search.domain.di.searchDomainModule
import com.app.movieapp.feature.search.presentation.di.searchPresentationModule
import com.app.movieapp.feature.settings.data.di.settingsDataModule
import com.app.movieapp.feature.settings.domain.di.settingsDomainModule
import com.app.movieapp.feature.settings.presentation.di.settingsPresentationModule
import com.app.movieapp.sync.SyncWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val appModule = module {
    single<ResourceProvider> { AndroidResourceProvider(androidContext()) }

    worker { SyncWorker(androidContext(), get(), get()) }
}

val allModules = listOf(
    networkModule,
    databaseModule,
    commonDataModule,
    appModule,

    moviesDomainModule, moviesDataModule, moviesPresentationModule,

    movieDetailsDomainModule, movieDetailsPresentationModule,

    searchDomainModule, searchDataModule, searchPresentationModule,

    favoritesDomainModule, favoritesDataModule, favoritesPresentationModule,

    settingsDomainModule, settingsDataModule, settingsPresentationModule,
)
