package com.app.movieapp.feature.moviedetails.presentation

import com.app.movieapp.common.presentation.mvi.Intent
import com.app.movieapp.feature.moviedetails.presentation.model.MovieDetailsUiModel

sealed interface MovieDetailsIntent : Intent {
    data object Load : MovieDetailsIntent
    data object Retry : MovieDetailsIntent
    data class Loaded(val movie: MovieDetailsUiModel) : MovieDetailsIntent
    data class Failed(val message: String) : MovieDetailsIntent

    data class FavoriteChanged(val isFavorite: Boolean) : MovieDetailsIntent

    data object ToggleFavorite : MovieDetailsIntent
}
