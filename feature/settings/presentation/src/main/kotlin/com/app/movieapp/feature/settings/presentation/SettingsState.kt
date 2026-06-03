package com.app.movieapp.feature.settings.presentation

import com.app.movieapp.common.presentation.mvi.UiState
import com.app.movieapp.core.contract.preferences.UserPreferences

/** Single source of truth for the settings screen: the current persisted preferences. */
data class SettingsState(
    val preferences: UserPreferences = UserPreferences(),
) : UiState
