package com.app.movieapp.feature.search.data.di

import com.app.movieapp.feature.search.data.remote.SearchApi
import com.app.movieapp.feature.search.data.repository.SearchRepositoryImpl
import com.app.movieapp.feature.search.domain.repository.SearchRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.bind
import org.koin.dsl.module

val searchDataModule = module {
    singleOf(::SearchApi)
    singleOf(::SearchRepositoryImpl) { bind<SearchRepository>() }
}
