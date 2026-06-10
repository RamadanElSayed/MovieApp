package com.app.movieapp.core.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun interface FeatureEntryProvider {
    fun EntryProviderScope<NavKey>.install(backStack: NavBackStack<NavKey>)
}

fun EntryProviderScope<NavKey>.install(
    provider: FeatureEntryProvider,
    backStack: NavBackStack<NavKey>,
) = with(provider) { install(backStack) }
