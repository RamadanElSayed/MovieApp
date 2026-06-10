package com.app.movieapp.feature.movieslist.presentation.di

import com.app.movieapp.core.navigation.FeatureEntryProvider
import com.app.movieapp.feature.movieslist.presentation.MoviesListViewModel
import com.app.movieapp.feature.movieslist.presentation.navigation.MoviesEntryProvider
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.bind
import org.koin.dsl.module

val moviesPresentationModule = module {
    viewModelOf(::MoviesListViewModel)
    singleOf(::MoviesEntryProvider) { bind<FeatureEntryProvider>() }
}
