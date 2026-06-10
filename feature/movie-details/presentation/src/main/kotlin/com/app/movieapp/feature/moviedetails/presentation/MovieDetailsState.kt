package com.app.movieapp.feature.moviedetails.presentation

import com.app.movieapp.common.presentation.mvi.UiState
import com.app.movieapp.feature.moviedetails.presentation.model.MovieDetailsUiModel

data class MovieDetailsState(
    val isLoading: Boolean = true,
    val movie: MovieDetailsUiModel? = null,
    val isFavorite: Boolean = false,
    val error: String? = null,
) : UiState
