package com.app.movieapp.feature.favorites.presentation

import com.app.movieapp.common.presentation.mvi.UiState
import com.app.movieapp.feature.favorites.presentation.model.FavoriteUiModel

/** Single source of truth for the favourites screen. */
data class FavoritesState(
    val isLoading: Boolean = true,
    val items: List<FavoriteUiModel> = emptyList(),
) : UiState {
    val isEmpty: Boolean get() = !isLoading && items.isEmpty()
}
