package com.app.movieapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.movieapp.core.database.dao.FavoriteMovieDao
import com.app.movieapp.core.database.dao.MovieDao
import com.app.movieapp.core.database.dao.MovieRemoteKeyDao
import com.app.movieapp.core.database.entity.FavoriteMovieEntity
import com.app.movieapp.core.database.entity.MovieEntity
import com.app.movieapp.core.database.entity.MovieRemoteKeyEntity

@Database(
    entities = [
        MovieEntity::class,
        MovieRemoteKeyEntity::class,
        FavoriteMovieEntity::class,
    ],
    version = 2,
    exportSchema = true,
)
abstract class MovieAppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieRemoteKeyDao(): MovieRemoteKeyDao
    abstract fun favoriteMovieDao(): FavoriteMovieDao

    companion object {
        const val NAME = "movieapp.db"
    }
}
