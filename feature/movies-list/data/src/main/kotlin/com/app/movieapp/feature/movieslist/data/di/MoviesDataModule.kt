package com.app.movieapp.feature.movieslist.data.di

import com.app.movieapp.core.contract.movies.MovieProvider
import com.app.movieapp.feature.movieslist.data.contract.MovieProviderImpl
import com.app.movieapp.feature.movieslist.data.remote.MoviesApi
import com.app.movieapp.feature.movieslist.data.repository.MoviesRepositoryImpl
import com.app.movieapp.feature.movieslist.domain.repository.MoviesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.bind
import org.koin.dsl.module

val moviesDataModule = module {
    singleOf(::MoviesApi)
    singleOf(::MoviesRepositoryImpl) { bind<MoviesRepository>() }
    singleOf(::MovieProviderImpl) { bind<MovieProvider>() }
}
