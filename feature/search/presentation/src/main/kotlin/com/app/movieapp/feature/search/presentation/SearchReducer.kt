package com.app.movieapp.feature.search.presentation

import com.app.movieapp.common.presentation.mvi.Reducer

class SearchReducer : Reducer<SearchState, SearchIntent> {
    override fun reduce(state: SearchState, intent: SearchIntent): SearchState =
        when (intent) {
            is SearchIntent.QueryChanged -> state.copy(query = intent.query, error = null)
            SearchIntent.Loading -> state.copy(isLoading = true, error = null)
            SearchIntent.Retry -> state.copy(isLoading = true, error = null)
            is SearchIntent.ResultsLoaded ->
                state.copy(isLoading = false, results = intent.results, error = null)
            is SearchIntent.Failed ->
                state.copy(isLoading = false, error = intent.message)
            is SearchIntent.OpenDetails -> state
        }
}
