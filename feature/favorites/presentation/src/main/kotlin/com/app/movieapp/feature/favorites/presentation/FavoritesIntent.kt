package com.app.movieapp.feature.favorites.presentation

import com.app.movieapp.common.presentation.mvi.Intent
import com.app.movieapp.feature.favorites.presentation.model.FavoriteUiModel

/** Everything the favourites screen (or its async work) can ask the ViewModel to do. */
sealed interface FavoritesIntent : Intent {
    data object Load : FavoritesIntent
    data class ItemsLoaded(val items: List<FavoriteUiModel>) : FavoritesIntent
    data class Remove(val movieId: Int) : FavoritesIntent
    data class OpenDetails(val movieId: Int) : FavoritesIntent
}
