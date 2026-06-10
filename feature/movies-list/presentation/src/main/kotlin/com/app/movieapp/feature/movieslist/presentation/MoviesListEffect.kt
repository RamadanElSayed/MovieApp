package com.app.movieapp.feature.movieslist.presentation

import com.app.movieapp.common.presentation.mvi.Effect

sealed interface MoviesListEffect : Effect {
    data class NavigateToDetails(val movieId: Int) : MoviesListEffect

    data class ShowSnackbar(val message: String) : MoviesListEffect
}
