package com.app.movieapp.feature.movieslist.domain.model

enum class MovieCategory(
    val apiPath: String,
) {
    POPULAR("movie/popular"),
    NOW_PLAYING("movie/now_playing"),
    TOP_RATED("movie/top_rated"),
    UPCOMING("movie/upcoming"),
}
