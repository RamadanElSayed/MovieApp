package com.app.movieapp.feature.movieslist.domain.di

import com.app.movieapp.feature.movieslist.domain.usecase.GetMovieUseCase
import com.app.movieapp.feature.movieslist.domain.usecase.ObservePagedMoviesUseCase
import com.app.movieapp.feature.movieslist.domain.usecase.RefreshMoviesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val moviesDomainModule =
    module {
        factoryOf(::ObservePagedMoviesUseCase)
        factoryOf(::RefreshMoviesUseCase)
        factoryOf(::GetMovieUseCase)
    }
