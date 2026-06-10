package com.app.movieapp.feature.search.presentation

import com.app.movieapp.common.presentation.mvi.Effect

sealed interface SearchEffect : Effect {
    data class NavigateToDetails(val movieId: Int) : SearchEffect
}
