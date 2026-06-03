package com.app.movieapp.feature.movieslist.presentation

import com.app.movieapp.common.presentation.mvi.UiState
import com.app.movieapp.feature.movieslist.domain.model.MovieCategory

/**
 * Screen-level state for the movies list. The paged content itself is exposed as a separate
 * `Flow<PagingData>` (Paging owns its own LoadState); this captures the selected category, refresh,
 * offline indication and one-shot/fatal errors.
 */
data class MoviesListState(
    val selectedCategory: MovieCategory = MovieCategory.POPULAR,
    val isRefreshing: Boolean = false,
    val isOffline: Boolean = false,
    /** A persistent, localized error shown as a full-screen retryable state (hard failure). */
    val fatalError: String? = null,
) : UiState
