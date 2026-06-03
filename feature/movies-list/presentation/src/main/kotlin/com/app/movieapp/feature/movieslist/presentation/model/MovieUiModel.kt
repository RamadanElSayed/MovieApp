package com.app.movieapp.feature.movieslist.presentation.model

import androidx.compose.runtime.Immutable
import com.app.movieapp.common.presentation.util.toRatingLabel
import com.app.movieapp.feature.movieslist.domain.model.Movie

/** Presentation model — shaped for display (e.g. a preformatted rating label, year, backdrop). */
@Immutable
data class MovieUiModel(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val backdropUrl: String,
    val ratingLabel: String,
    val year: String,
    val isFavorite: Boolean = false,
)

/** Domain -> UI mapper (independently unit-tested). */
fun Movie.toUi(isFavorite: Boolean = false): MovieUiModel = MovieUiModel(
    id = id,
    title = title,
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    ratingLabel = rating.toRatingLabel(),
    year = releaseDate.take(4),
    isFavorite = isFavorite,
)
