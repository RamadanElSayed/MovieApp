package com.app.movieapp.feature.settings.presentation

import com.app.movieapp.common.presentation.mvi.Reducer

/**
 * Reducer optimistically reflects selections in state; the ViewModel persists them to DataStore.
 * Because the screen also observes the persisted Flow, state converges to the stored value.
 */
class SettingsReducer : Reducer<SettingsState, SettingsIntent> {
    override fun reduce(state: SettingsState, intent: SettingsIntent): SettingsState =
        when (intent) {
            is SettingsIntent.PreferencesLoaded -> state.copy(preferences = intent.preferences)
            is SettingsIntent.SelectTheme ->
                state.copy(preferences = state.preferences.copy(themeMode = intent.mode))
            is SettingsIntent.SelectLanguage ->
                state.copy(preferences = state.preferences.copy(language = intent.language))
            is SettingsIntent.SelectDynamicColor ->
                state.copy(preferences = state.preferences.copy(dynamicColor = intent.value))
        }
}
