package com.app.movieapp.feature.settings.presentation

import com.app.movieapp.common.presentation.mvi.Intent
import com.app.movieapp.core.contract.preferences.AppLanguage
import com.app.movieapp.core.contract.preferences.DynamicColor
import com.app.movieapp.core.contract.preferences.ThemeMode
import com.app.movieapp.core.contract.preferences.UserPreferences

sealed interface SettingsIntent : Intent {
    data class PreferencesLoaded(val preferences: UserPreferences) : SettingsIntent
    data class SelectTheme(val mode: ThemeMode) : SettingsIntent
    data class SelectLanguage(val language: AppLanguage) : SettingsIntent
    data class SelectDynamicColor(val value: DynamicColor) : SettingsIntent
}
