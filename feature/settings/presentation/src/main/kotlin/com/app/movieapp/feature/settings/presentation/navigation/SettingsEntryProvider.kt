package com.app.movieapp.feature.settings.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.app.movieapp.core.navigation.FeatureEntryProvider
import com.app.movieapp.core.navigation.Settings
import com.app.movieapp.feature.settings.presentation.SettingsRoute

class SettingsEntryProvider : FeatureEntryProvider {
    override fun EntryProviderScope<NavKey>.install(backStack: NavBackStack<NavKey>) {
        entry<Settings> { SettingsRoute() }
    }
}
