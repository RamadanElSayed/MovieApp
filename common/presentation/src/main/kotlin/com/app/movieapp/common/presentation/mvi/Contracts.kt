package com.app.movieapp.common.presentation.mvi

/** Marker for an immutable UI state object (single source of truth for a screen). */
interface UiState

/** Marker for a user/UI intent (the only way to mutate state). */
interface Intent

/** Marker for a one-shot side-effect (snackbar, navigation, toast) — not part of state. */
interface Effect

/**
 * Pure, side-effect-free state transition. Given the current [state] and an [intent], produce the
 * next state. Keeping reductions pure makes them trivially unit-testable and predictable.
 */
fun interface Reducer<S : UiState, I : Intent> {
    fun reduce(state: S, intent: I): S
}
