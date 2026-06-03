package com.app.movieapp.feature.search.presentation.model

import androidx.compose.runtime.Immutable
import com.app.movieapp.common.presentation.util.toRatingLabel
import com.app.movieapp.feature.search.domain.model.SearchMovie

/** Presentation model for a single search result — shaped for display (preformatted rating). */
@Immutable
data class SearchResultUiModel(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val ratingLabel: String,
)

/** Domain -> UI mapper for search results. */
fun SearchMovie.toUi(): SearchResultUiModel = SearchResultUiModel(
    id = id,
    title = title,
    posterUrl = posterUrl,
    ratingLabel = rating.toRatingLabel(),
)
