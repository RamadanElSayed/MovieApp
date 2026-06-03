
package com.app.movieapp.core.contract.preferences

import kotlinx.coroutines.flow.Flow

/** User-selectable theme mode. Owned here so `core:design-system` and `feature:settings` agree. */
enum class ThemeMode { LIGHT, DARK, SYSTEM }

/** Whether to use Material You dynamic color (Android 12+) or the static brand palette. */
enum class DynamicColor { ENABLED, DISABLED }

/** Supported in-app languages (BCP-47 tags drive AppCompatDelegate.setApplicationLocales). */
enum class AppLanguage(val tag: String) { ENGLISH("en"), ARABIC("ar") }

/** Immutable snapshot of all persisted user preferences. */
data class UserPreferences(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val dynamicColor: DynamicColor = DynamicColor.ENABLED,
    val language: AppLanguage = AppLanguage.ENGLISH,
    /** False until the user finishes the first-launch onboarding. */
    val onboardingCompleted: Boolean = false,
)

/**
 * Preferences contract. IMPLEMENTED by `feature:settings` (DataStore-backed) and consumed by:
 *  - `:app` / `core:design-system` to drive theme + layout direction at the composition root,
 *  - any feature that needs the current locale/theme — all without depending on `feature:settings`.
 */
interface UserPreferencesRepository {
    val preferences: Flow<UserPreferences>
    suspend fun setThemeMode(mode: ThemeMode)
    suspend fun setDynamicColor(value: DynamicColor)
    suspend fun setLanguage(language: AppLanguage)
    suspend fun setOnboardingCompleted(completed: Boolean)
}
