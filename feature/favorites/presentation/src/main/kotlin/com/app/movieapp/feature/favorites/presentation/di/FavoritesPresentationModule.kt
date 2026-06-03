package com.app.movieapp.feature.favorites.presentation.di

import com.app.movieapp.core.navigation.FeatureEntryProvider
import com.app.movieapp.feature.favorites.presentation.FavoritesViewModel
import com.app.movieapp.feature.favorites.presentation.navigation.FavoritesEntryProvider
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.bind
import org.koin.dsl.module

val favoritesPresentationModule = module {
    viewModelOf(::FavoritesViewModel)
    singleOf(::FavoritesEntryProvider) { bind<FeatureEntryProvider>() }
}
