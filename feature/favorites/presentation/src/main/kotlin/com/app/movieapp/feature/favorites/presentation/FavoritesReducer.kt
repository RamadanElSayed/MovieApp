package com.app.movieapp.feature.favorites.presentation

import com.app.movieapp.common.presentation.mvi.Reducer

class FavoritesReducer : Reducer<FavoritesState, FavoritesIntent> {
    override fun reduce(state: FavoritesState, intent: FavoritesIntent): FavoritesState =
        when (intent) {
            FavoritesIntent.Load -> state.copy(isLoading = true)
            is FavoritesIntent.ItemsLoaded -> state.copy(isLoading = false, items = intent.items)
            is FavoritesIntent.Remove,
            is FavoritesIntent.OpenDetails,
            -> state
        }
}
