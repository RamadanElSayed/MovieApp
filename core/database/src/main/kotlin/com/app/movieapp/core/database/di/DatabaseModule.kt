package com.app.movieapp.core.database.di

import androidx.room.Room
import com.app.movieapp.core.database.MovieAppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/** Koin module exposing the database + its DAOs as singletons. */
val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieAppDatabase::class.java,
            MovieAppDatabase.NAME,
        ).fallbackToDestructiveMigration(dropAllTables = true).build()
    }
    single { get<MovieAppDatabase>().movieDao() }
    single { get<MovieAppDatabase>().movieRemoteKeyDao() }
    single { get<MovieAppDatabase>().favoriteMovieDao() }
}
