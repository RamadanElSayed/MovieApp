package com.app.movieapp.core.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

/**
 * Each feature contributes ONE of these to map its [NavKey]s to its Compose screens.
 *
 * The `:app` module collects every registered [FeatureEntryProvider] from Koin (`getAll()`) and
 * installs them into the single `NavDisplay` — so the app never imports a feature's internals, and
 * features never import one another. To navigate, a screen mutates [backStack] directly
 * (e.g. `backStack.add(MovieDetails(id))`).
 */
fun interface FeatureEntryProvider {
    fun EntryProviderScope<NavKey>.install(backStack: NavBackStack<NavKey>)
}

/** Applies a [FeatureEntryProvider] to this builder. Used by `:app` when assembling NavDisplay. */
fun EntryProviderScope<NavKey>.install(
    provider: FeatureEntryProvider,
    backStack: NavBackStack<NavKey>,
) = with(provider) { install(backStack) }
