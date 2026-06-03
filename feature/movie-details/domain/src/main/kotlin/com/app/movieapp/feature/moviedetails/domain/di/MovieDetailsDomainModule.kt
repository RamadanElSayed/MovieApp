package com.app.movieapp.feature.moviedetails.domain.di

import com.app.movieapp.feature.moviedetails.domain.usecase.GetMovieDetailsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val movieDetailsDomainModule = module {
    factoryOf(::GetMovieDetailsUseCase)
}
