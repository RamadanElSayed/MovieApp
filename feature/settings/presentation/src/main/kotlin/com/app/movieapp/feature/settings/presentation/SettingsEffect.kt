package com.app.movieapp.feature.settings.presentation

import com.app.movieapp.common.presentation.mvi.Effect

/** One-shot events emitted by the settings ViewModel (never part of state). */
sealed interface SettingsEffect : Effect {
    /** Tells the host Activity to apply a new per-app locale immediately. */
    data class ApplyLanguage(val tag: String) : SettingsEffect
}
