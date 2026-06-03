package com.app.movieapp.feature.movieslist.presentation

import com.app.movieapp.common.presentation.mvi.Effect

/** One-shot events emitted by the movies-list ViewModel (never part of state). */
sealed interface MoviesListEffect : Effect {
    /** Navigate to details — handled by the screen, which mutates the Nav3 back stack. */
    data class NavigateToDetails(val movieId: Int) : MoviesListEffect
    /** Transient error shown as a snackbar (soft failure) — localized text. */
    data class ShowSnackbar(val message: String) : MoviesListEffect
}
