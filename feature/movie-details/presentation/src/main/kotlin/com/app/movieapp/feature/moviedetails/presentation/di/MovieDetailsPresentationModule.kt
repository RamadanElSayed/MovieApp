package com.app.movieapp.feature.moviedetails.presentation.di

import com.app.movieapp.core.navigation.FeatureEntryProvider
import com.app.movieapp.feature.moviedetails.presentation.MovieDetailsViewModel
import com.app.movieapp.feature.moviedetails.presentation.navigation.MovieDetailsEntryProvider
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.bind
import org.koin.dsl.module

val movieDetailsPresentationModule = module {
    viewModel { (movieId: Int) -> MovieDetailsViewModel(movieId, get(), get(), get()) }
    singleOf(::MovieDetailsEntryProvider) { bind<FeatureEntryProvider>() }
}
