package com.app.movieapp.core.database.entity

import androidx.room.Entity

/**
 * Paging 3 RemoteMediator bookkeeping: which page precedes/follows a cached movie.
 * Keyed by `(movieId, category)` so every category list keeps its own paging cursor.
 */
@Entity(tableName = "movie_remote_keys", primaryKeys = ["movieId", "category"])
data class MovieRemoteKeyEntity(
    val movieId: Int,
    val category: String,
    val prevKey: Int?,
    val nextKey: Int?,
    /** When this page was last fetched — used to decide cache freshness. */
    val lastUpdated: Long,
)
