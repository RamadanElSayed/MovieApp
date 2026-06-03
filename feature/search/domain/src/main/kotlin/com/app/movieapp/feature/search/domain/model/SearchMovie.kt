package com.app.movieapp.feature.search.domain.model

/** search owns its own domain model — it does not reuse another feature's model. */
data class SearchMovie(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val rating: Double,
)
