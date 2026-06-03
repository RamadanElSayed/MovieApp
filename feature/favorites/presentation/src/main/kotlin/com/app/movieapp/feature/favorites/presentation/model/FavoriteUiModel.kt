package com.app.movieapp.feature.favorites.presentation.model

import androidx.compose.runtime.Immutable
import com.app.movieapp.common.presentation.util.toRatingLabel
import com.app.movieapp.core.contract.movies.MovieSummary

/** Presentation model for a single favourite movie — shaped for display (preformatted rating). */
@Immutable
data class FavoriteUiModel(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val ratingLabel: String,
)

/** Domain summary -> UI mapper. */
fun MovieSummary.toUi(): FavoriteUiModel = FavoriteUiModel(
    id = id,
    title = title,
    posterUrl = posterUrl,
    ratingLabel = rating.toRatingLabel(),
)
