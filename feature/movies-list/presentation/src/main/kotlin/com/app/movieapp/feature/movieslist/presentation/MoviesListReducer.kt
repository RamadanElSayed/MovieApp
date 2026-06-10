package com.app.movieapp.feature.movieslist.presentation

import com.app.movieapp.common.presentation.mvi.Reducer

class MoviesListReducer : Reducer<MoviesListState, MoviesListIntent> {
    override fun reduce(state: MoviesListState, intent: MoviesListIntent): MoviesListState =
        when (intent) {
            MoviesListIntent.Load -> state
            MoviesListIntent.Refresh,
            MoviesListIntent.Retry,
            -> state.copy(isRefreshing = true, fatalError = null)

            is MoviesListIntent.RefreshFinished -> state.copy(
                isRefreshing = false,

                fatalError = intent.errorMessage ?: state.fatalError,
            )

            is MoviesListIntent.ConnectivityChanged -> state.copy(isOffline = intent.isOffline)

            is MoviesListIntent.SelectCategory -> state.copy(
                selectedCategory = intent.category,
                fatalError = null,
            )

            is MoviesListIntent.OpenDetails,
            is MoviesListIntent.ToggleFavorite,
            -> state
        }
}
