package com.app.movieapp.feature.movieslist.presentation

import com.app.movieapp.common.presentation.mvi.Intent
import com.app.movieapp.feature.movieslist.domain.model.MovieCategory

/** Everything the movies-list screen (or its async work) can ask the ViewModel to do. */
sealed interface MoviesListIntent : Intent {
    data object Load : MoviesListIntent
    data object Refresh : MoviesListIntent
    data object Retry : MoviesListIntent
    data class SelectCategory(val category: MovieCategory) : MoviesListIntent
    data class OpenDetails(val movieId: Int) : MoviesListIntent
    data class ToggleFavorite(val movieId: Int) : MoviesListIntent
    data class ConnectivityChanged(val isOffline: Boolean) : MoviesListIntent
    data class RefreshFinished(val errorMessage: String?) : MoviesListIntent
}
