package com.app.movieapp.common.presentation.mvi

interface UiState

interface Intent

interface Effect

fun interface Reducer<S : UiState, I : Intent> {
    fun reduce(state: S, intent: I): S
}
