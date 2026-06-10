package com.app.movieapp.feature.settings.presentation

import com.app.movieapp.common.presentation.mvi.UiState
import com.app.movieapp.core.contract.preferences.UserPreferences

data class SettingsState(
    val preferences: UserPreferences = UserPreferences(),
) : UiState
