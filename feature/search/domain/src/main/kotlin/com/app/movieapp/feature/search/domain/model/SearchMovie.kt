package com.app.movieapp.feature.search.domain.model

data class SearchMovie(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val rating: Double,
)
