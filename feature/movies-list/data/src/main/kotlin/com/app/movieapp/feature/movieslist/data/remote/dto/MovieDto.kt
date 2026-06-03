package com.app.movieapp.feature.movieslist.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Network DTO — matches the TMDB JSON shape exactly. Never leaves the data layer. */
@Serializable
data class MovieDto(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String = "",
    @SerialName("overview") val overview: String = "",
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("vote_average") val voteAverage: Double = 0.0,
    @SerialName("release_date") val releaseDate: String = "",
    @SerialName("popularity") val popularity: Double = 0.0,
)

/** TMDB paged envelope, e.g. `/movie/popular`. */
@Serializable
data class MoviesPageDto(
    @SerialName("page") val page: Int,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("results") val results: List<MovieDto> = emptyList(),
)
