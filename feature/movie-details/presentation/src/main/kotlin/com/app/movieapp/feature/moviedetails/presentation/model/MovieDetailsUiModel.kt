package com.app.movieapp.feature.moviedetails.presentation.model

import androidx.compose.runtime.Immutable
import com.app.movieapp.common.presentation.util.toRatingLabel
import com.app.movieapp.core.contract.movies.MovieSummary

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

fun MovieSummary.toUi(): MovieDetailsUiModel = MovieDetailsUiModel(
    id = id,
    title = title,
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    ratingLabel = rating.toRatingLabel(),
    year = releaseDate.take(4),
    overview = overview,
)
