package com.app.movieapp.core.database.di

import androidx.room.Room
import com.app.movieapp.core.database.MovieAppDatabase
import com.app.movieapp.core.database.migration.MovieAppMigrations
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieAppDatabase::class.java,
            MovieAppDatabase.NAME,
        ).addMigrations(*MovieAppMigrations)
            .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
            .build()
    }
    single { get<MovieAppDatabase>().movieDao() }
    single { get<MovieAppDatabase>().movieRemoteKeyDao() }
    single { get<MovieAppDatabase>().favoriteMovieDao() }
}
