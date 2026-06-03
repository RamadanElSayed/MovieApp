package com.app.movieapp.feature.favorites.data.di

import com.app.movieapp.core.contract.favorites.FavoritesProvider
import com.app.movieapp.feature.favorites.data.contract.FavoritesProviderImpl
import com.app.movieapp.feature.favorites.data.repository.FavoritesRepositoryImpl
import com.app.movieapp.feature.favorites.domain.repository.FavoritesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.bind
import org.koin.dsl.module

val favoritesDataModule = module {
    single<FavoritesRepository> { FavoritesRepositoryImpl(dao = get()) }
    singleOf(::FavoritesProviderImpl) { bind<FavoritesProvider>() }
}
