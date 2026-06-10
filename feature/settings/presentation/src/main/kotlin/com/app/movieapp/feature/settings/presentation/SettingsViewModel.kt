package com.app.movieapp.feature.settings.presentation

import androidx.lifecycle.viewModelScope
import com.app.movieapp.common.presentation.mvi.BaseViewModel
import com.app.movieapp.feature.settings.domain.usecase.ObservePreferencesUseCase
import com.app.movieapp.feature.settings.domain.usecase.SetDynamicColorUseCase
import com.app.movieapp.feature.settings.domain.usecase.SetLanguageUseCase
import com.app.movieapp.feature.settings.domain.usecase.SetThemeModeUseCase
import kotlinx.coroutines.launch

class SettingsViewModel(
    observePreferences: ObservePreferencesUseCase,
    private val setThemeMode: SetThemeModeUseCase,
    private val setLanguage: SetLanguageUseCase,
    private val setDynamicColor: SetDynamicColorUseCase,
) : BaseViewModel<SettingsState, SettingsIntent, SettingsEffect>(
    initialState = SettingsState(),
    reducer = SettingsReducer(),
) {
    init {
        viewModelScope.launch {
            observePreferences(Unit).collect { sendIntent(SettingsIntent.PreferencesLoaded(it)) }
        }
    }

    override fun handleIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.SelectTheme ->
                viewModelScope.launch { setThemeMode(intent.mode) }
            is SettingsIntent.SelectDynamicColor ->
                viewModelScope.launch { setDynamicColor(intent.value) }
            is SettingsIntent.SelectLanguage -> {
                viewModelScope.launch { setLanguage(intent.language) }

                sendEffect(SettingsEffect.ApplyLanguage(intent.language.tag))
            }
            is SettingsIntent.PreferencesLoaded -> Unit
        }
    }
}
