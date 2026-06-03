package com.app.movieapp.feature.moviedetails.presentation.model

import androidx.compose.runtime.Immutable
import com.app.movieapp.common.presentation.util.toRatingLabel
import com.app.movieapp.core.contract.movies.MovieSummary

/** Presentation model for the movie-details screen — shaped for display. */
@Immutable
data class MovieDetailsUiModel(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val backdropUrl: String,
    val ratingLabel: String,
    val year: String,
    val overview: String,
)

/** Domain summary -> UI mapper. Derives a 4-digit year and a one-decimal rating label. */
fun MovieSummary.toUi(): MovieDetailsUiModel = MovieDetailsUiModel(
    id = id,
    title = title,
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    ratingLabel = rating.toRatingLabel(),
    year = releaseDate.take(4),
    overview = overview,
)
