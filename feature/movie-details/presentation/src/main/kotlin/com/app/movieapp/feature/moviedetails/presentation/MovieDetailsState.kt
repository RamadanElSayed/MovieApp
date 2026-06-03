package com.app.movieapp.feature.moviedetails.presentation

import com.app.movieapp.common.presentation.mvi.UiState
import com.app.movieapp.feature.moviedetails.presentation.model.MovieDetailsUiModel

/** Single source of truth for the movie-details screen. */
data class MovieDetailsState(
    val isLoading: Boolean = true,
    val movie: MovieDetailsUiModel? = null,
    val isFavorite: Boolean = false,
    val error: String? = null,
) : UiState
