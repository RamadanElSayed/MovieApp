package com.app.movieapp.core.database.entity

import androidx.room.Entity

@Entity(tableName = "movies", primaryKeys = ["id", "category"])
data class MovieEntity(
    val id: Int,

    val category: String,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val releaseDate: String,
    val popularity: Double,

    val page: Int,
    val positionInPage: Int,
)
