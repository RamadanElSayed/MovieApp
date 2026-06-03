package com.app.movieapp.feature.moviedetails.presentation

import com.app.movieapp.common.presentation.mvi.Intent
import com.app.movieapp.feature.moviedetails.presentation.model.MovieDetailsUiModel

/** Everything the movie-details screen (or its async work) can ask the ViewModel to do. */
sealed interface MovieDetailsIntent : Intent {
    data object Load : MovieDetailsIntent
    data object Retry : MovieDetailsIntent
    data class Loaded(val movie: MovieDetailsUiModel) : MovieDetailsIntent
    data class Failed(val message: String) : MovieDetailsIntent
    /** Emitted when the favourite status for this movie changes in the store. */
    data class FavoriteChanged(val isFavorite: Boolean) : MovieDetailsIntent
    /** User tapped the heart — toggles favourite (handled as a side-effect). */
    data object ToggleFavorite : MovieDetailsIntent
}
