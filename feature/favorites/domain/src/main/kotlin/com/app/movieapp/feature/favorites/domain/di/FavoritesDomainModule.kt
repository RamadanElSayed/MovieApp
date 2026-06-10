package com.app.movieapp.feature.favorites.domain.di

import com.app.movieapp.feature.favorites.domain.usecase.ObserveFavoriteMoviesUseCase
import com.app.movieapp.feature.favorites.domain.usecase.ToggleFavoriteUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val favoritesDomainModule =
    module {
        factoryOf(::ObserveFavoriteMoviesUseCase)
        factoryOf(::ToggleFavoriteUseCase)
    }
