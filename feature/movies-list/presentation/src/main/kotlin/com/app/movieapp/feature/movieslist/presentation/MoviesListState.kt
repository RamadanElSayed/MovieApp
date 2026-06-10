package com.app.movieapp.feature.movieslist.presentation

import com.app.movieapp.common.presentation.mvi.UiState
import com.app.movieapp.feature.movieslist.domain.model.MovieCategory

data class MoviesListState(
    val selectedCategory: MovieCategory = MovieCategory.POPULAR,
    val isRefreshing: Boolean = false,
    val isOffline: Boolean = false,

    val fatalError: String? = null,
) : UiState
