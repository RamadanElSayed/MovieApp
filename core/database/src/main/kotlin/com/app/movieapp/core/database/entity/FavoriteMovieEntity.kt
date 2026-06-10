package com.app.movieapp.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey val movieId: Int,
    val favoritedAt: Long,
    val pendingSync: Boolean = true,
)
