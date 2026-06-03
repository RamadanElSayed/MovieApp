package com.app.movieapp.feature.search.presentation

import com.app.movieapp.common.presentation.mvi.Effect

/** One-shot events emitted by the search ViewModel (never part of state). */
sealed interface SearchEffect : Effect {
    data class NavigateToDetails(val movieId: Int) : SearchEffect
}
