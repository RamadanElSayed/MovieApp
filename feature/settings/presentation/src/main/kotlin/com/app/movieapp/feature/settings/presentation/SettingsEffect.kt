package com.app.movieapp.feature.settings.presentation

import com.app.movieapp.common.presentation.mvi.Effect

sealed interface SettingsEffect : Effect {
    data class ApplyLanguage(val tag: String) : SettingsEffect
}
