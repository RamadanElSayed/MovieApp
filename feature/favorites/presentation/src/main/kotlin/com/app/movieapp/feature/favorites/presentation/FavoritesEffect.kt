package com.app.movieapp.feature.favorites.presentation

import com.app.movieapp.common.presentation.mvi.Effect

/** One-shot events emitted by the favourites ViewModel (never part of state). */
sealed interface FavoritesEffect : Effect {
    data class NavigateToDetails(val movieId: Int) : FavoritesEffect
}
