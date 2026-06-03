package com.app.movieapp.feature.moviedetails.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.app.movieapp.core.navigation.FeatureEntryProvider
import com.app.movieapp.core.navigation.MovieDetails
import com.app.movieapp.feature.moviedetails.presentation.MovieDetailsRoute

class MovieDetailsEntryProvider : FeatureEntryProvider {
    override fun EntryProviderScope<NavKey>.install(backStack: NavBackStack<NavKey>) {
        // The typed key carries the id — no string routes, no manual arg parsing.
        entry<MovieDetails> { key ->
            MovieDetailsRoute(
                movieId = key.id,
                onBack = { backStack.removeLastOrNull() },
            )
        }
    }
}
