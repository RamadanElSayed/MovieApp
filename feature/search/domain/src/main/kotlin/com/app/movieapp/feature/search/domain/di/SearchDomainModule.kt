package com.app.movieapp.feature.search.domain.di

import com.app.movieapp.feature.search.domain.usecase.SearchMoviesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val searchDomainModule = module {
    factoryOf(::SearchMoviesUseCase)
}
