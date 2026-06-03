package com.app.movieapp.feature.search.presentation

import com.app.movieapp.common.presentation.mvi.UiState
import com.app.movieapp.feature.search.presentation.model.SearchResultUiModel

/** Single source of truth for the search screen. */
data class SearchState(
    val query: String = "",
    val isLoading: Boolean = false,
    val results: List<SearchResultUiModel> = emptyList(),
    val error: String? = null,
) : UiState {
    val showEmpty: Boolean get() = !isLoading && error == null && query.isNotBlank() && results.isEmpty()
}
