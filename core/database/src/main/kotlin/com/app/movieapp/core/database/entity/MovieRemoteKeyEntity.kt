package com.app.movieapp.core.database.entity

import androidx.room.Entity

@Entity(tableName = "movie_remote_keys", primaryKeys = ["movieId", "category"])
data class MovieRemoteKeyEntity(
    val movieId: Int,
    val category: String,
    val prevKey: Int?,
    val nextKey: Int?,

    val lastUpdated: Long,
)
