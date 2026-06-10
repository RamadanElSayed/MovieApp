package com.app.movieapp.feature.search.presentation.model

import androidx.compose.runtime.Immutable
import com.app.movieapp.common.presentation.util.toRatingLabel
import com.app.movieapp.feature.search.domain.model.SearchMovie

@Immutable
data class SearchResultUiModel(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val ratingLabel: String,
)

fun SearchMovie.toUi(): SearchResultUiModel = SearchResultUiModel(
    id = id,
    title = title,
    posterUrl = posterUrl,
    ratingLabel = rating.toRatingLabel(),
)
