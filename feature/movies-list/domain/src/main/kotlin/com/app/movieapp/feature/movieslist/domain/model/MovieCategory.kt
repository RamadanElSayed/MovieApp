package com.app.movieapp.feature.movieslist.domain.model

/**
 * The TMDB movie lists the Home screen can browse. [apiPath] is the TMDB endpoint; the enum name is
 * also used as the per-category cache key in Room so each list pages independently, offline-first.
 */
enum class MovieCategory(val apiPath: String) {
    POPULAR("movie/popular"),
    NOW_PLAYING("movie/now_playing"),
    TOP_RATED("movie/top_rated"),
    UPCOMING("movie/upcoming"),
}
