package com.app.movieapp.feature.search.presentation

import com.app.movieapp.common.presentation.mvi.Intent
import com.app.movieapp.feature.search.presentation.model.SearchResultUiModel

sealed interface SearchIntent : Intent {
    data class QueryChanged(val query: String) : SearchIntent
    data object Retry : SearchIntent
    data class ResultsLoaded(val results: List<SearchResultUiModel>) : SearchIntent
    data class Failed(val message: String) : SearchIntent
    data object Loading : SearchIntent
    data class OpenDetails(val movieId: Int) : SearchIntent
}
