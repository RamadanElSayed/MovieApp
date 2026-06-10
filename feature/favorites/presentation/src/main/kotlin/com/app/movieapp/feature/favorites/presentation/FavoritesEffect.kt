package com.app.movieapp.feature.favorites.presentation

import com.app.movieapp.common.presentation.mvi.Effect

sealed interface FavoritesEffect : Effect {
    data class NavigateToDetails(val movieId: Int) : FavoritesEffect
}
