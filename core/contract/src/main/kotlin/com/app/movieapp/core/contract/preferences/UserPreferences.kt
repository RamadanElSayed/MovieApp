package com.app.movieapp.core.contract.preferences

import kotlinx.coroutines.flow.Flow

enum class ThemeMode { LIGHT, DARK, SYSTEM }

enum class DynamicColor { ENABLED, DISABLED }

enum class AppLanguage(
    val tag: String,
) {
    ENGLISH("en"),
    ARABIC("ar"),
}

data class UserPreferences(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val dynamicColor: DynamicColor = DynamicColor.ENABLED,
    val language: AppLanguage = AppLanguage.ENGLISH,
    val onboardingCompleted: Boolean = false,
)

interface UserPreferencesRepository {
    val preferences: Flow<UserPreferences>

    suspend fun setThemeMode(mode: ThemeMode)

    suspend fun setDynamicColor(value: DynamicColor)

    suspend fun setLanguage(language: AppLanguage)

    suspend fun setOnboardingCompleted(completed: Boolean)
}
