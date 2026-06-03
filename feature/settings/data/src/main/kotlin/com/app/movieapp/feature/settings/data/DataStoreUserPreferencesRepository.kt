package com.app.movieapp.feature.settings.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.app.movieapp.core.contract.preferences.AppLanguage
import com.app.movieapp.core.contract.preferences.DynamicColor
import com.app.movieapp.core.contract.preferences.ThemeMode
import com.app.movieapp.core.contract.preferences.UserPreferences
import com.app.movieapp.core.contract.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

/**
 * DataStore-backed implementation of the [UserPreferencesRepository] contract. settings OWNS this;
 * `:app` / `core:design-system` consume the contract to drive theme + locale, never this class.
 */
class DataStoreUserPreferencesRepository(
    context: Context,
) : UserPreferencesRepository {

    private val store: DataStore<Preferences> = context.dataStore

    override val preferences: Flow<UserPreferences> = store.data.map { prefs ->
        UserPreferences(
            themeMode = prefs[KEY_THEME]?.let { runCatching { ThemeMode.valueOf(it) }.getOrNull() }
                ?: ThemeMode.SYSTEM,
            dynamicColor = prefs[KEY_DYNAMIC]?.let { runCatching { DynamicColor.valueOf(it) }.getOrNull() }
                ?: DynamicColor.ENABLED,
            language = prefs[KEY_LANG]?.let { tag -> AppLanguage.entries.firstOrNull { it.tag == tag } }
                ?: AppLanguage.ENGLISH,
            onboardingCompleted = prefs[KEY_ONBOARDING] ?: false,
        )
    }

    override suspend fun setThemeMode(mode: ThemeMode) {
        store.edit { it[KEY_THEME] = mode.name }
    }

    override suspend fun setDynamicColor(value: DynamicColor) {
        store.edit { it[KEY_DYNAMIC] = value.name }
    }

    override suspend fun setLanguage(language: AppLanguage) {
        store.edit { it[KEY_LANG] = language.tag }
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        store.edit { it[KEY_ONBOARDING] = completed }
    }

    private companion object {
        val KEY_THEME = stringPreferencesKey("theme_mode")
        val KEY_DYNAMIC = stringPreferencesKey("dynamic_color")
        val KEY_LANG = stringPreferencesKey("language")
        val KEY_ONBOARDING = booleanPreferencesKey("onboarding_completed")
    }
}
