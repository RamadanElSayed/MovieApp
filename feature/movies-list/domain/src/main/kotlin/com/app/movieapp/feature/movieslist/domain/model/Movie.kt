package com.app.movieapp.feature.movieslist.domain.model

/**
 * Pure domain model — independent of API (DTO), DB (Entity) and UI (UiModel) shapes.
 * This is what use cases and the repository contract speak in.
 */
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val backdropUrl: String,
    val rating: Double,
    val releaseDate: String,
    val popularity: Double,
)
