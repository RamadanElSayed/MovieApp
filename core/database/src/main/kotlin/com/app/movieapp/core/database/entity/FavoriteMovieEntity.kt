package com.app.movieapp.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Favourites are stored locally for offline-first optimistic writes.
 * [pendingSync] marks rows whose backend sync hasn't been confirmed yet (reconcile on reconnect).
 */
@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey val movieId: Int,
    val favoritedAt: Long,
    val pendingSync: Boolean = true,
)
