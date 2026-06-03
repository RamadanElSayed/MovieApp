package com.app.movieapp.feature.moviedetails.presentation

import com.app.movieapp.common.presentation.mvi.Reducer

class MovieDetailsReducer : Reducer<MovieDetailsState, MovieDetailsIntent> {
    override fun reduce(state: MovieDetailsState, intent: MovieDetailsIntent): MovieDetailsState =
        when (intent) {
            MovieDetailsIntent.Load, MovieDetailsIntent.Retry ->
                state.copy(isLoading = true, error = null)
            is MovieDetailsIntent.Loaded ->
                state.copy(isLoading = false, movie = intent.movie, error = null)
            is MovieDetailsIntent.Failed ->
                state.copy(isLoading = false, error = intent.message)
            is MovieDetailsIntent.FavoriteChanged ->
                state.copy(isFavorite = intent.isFavorite)
            // Pure side-effect: the actual toggle happens in the ViewModel; the favourites flow
            // then emits FavoriteChanged, which updates state.
            MovieDetailsIntent.ToggleFavorite -> state
        }
}
