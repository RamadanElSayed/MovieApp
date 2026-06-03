package com.app.movieapp.feature.search.presentation.di

import com.app.movieapp.core.navigation.FeatureEntryProvider
import com.app.movieapp.feature.search.presentation.SearchViewModel
import com.app.movieapp.feature.search.presentation.navigation.SearchEntryProvider
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.bind
import org.koin.dsl.module

val searchPresentationModule = module {
    viewModelOf(::SearchViewModel)
    singleOf(::SearchEntryProvider) { bind<FeatureEntryProvider>() }
}
