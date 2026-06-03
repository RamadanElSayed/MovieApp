package com.app.movieapp.feature.movieslist.presentation

import com.app.movieapp.common.presentation.mvi.Reducer

/**
 * Pure, side-effect-free state transitions. No coroutines, no repositories — trivially testable.
 * Async work (refresh, navigation, favourite toggles) is handled by the ViewModel's intent handler.
 */
class MoviesListReducer : Reducer<MoviesListState, MoviesListIntent> {
    override fun reduce(state: MoviesListState, intent: MoviesListIntent): MoviesListState =
        when (intent) {
            MoviesListIntent.Load -> state
            MoviesListIntent.Refresh,
            MoviesListIntent.Retry,
            -> state.copy(isRefreshing = true, fatalError = null)

            is MoviesListIntent.RefreshFinished -> state.copy(
                isRefreshing = false,
                // Only escalate to a fatal (full-screen) error when there is nothing cached to show;
                // the ViewModel decides hard-vs-soft and only sets a message for hard failures here.
                fatalError = intent.errorMessage ?: state.fatalError,
            )

            is MoviesListIntent.ConnectivityChanged -> state.copy(isOffline = intent.isOffline)

            is MoviesListIntent.SelectCategory -> state.copy(
                selectedCategory = intent.category,
                fatalError = null,
            )

            // Navigation & favourite toggles produce effects/side-effects, not state changes.
            is MoviesListIntent.OpenDetails,
            is MoviesListIntent.ToggleFavorite,
            -> state
        }
}
