package com.app.movieapp.feature.movieslist.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.app.movieapp.core.navigation.FeatureEntryProvider
import com.app.movieapp.core.navigation.MovieDetails
import com.app.movieapp.core.navigation.MoviesList
import com.app.movieapp.feature.movieslist.presentation.MoviesListRoute

/**
 * movies-list contributes its own entries to the app's single NavDisplay. It pushes another
 * feature's key ([MovieDetails]) WITHOUT depending on that feature — the key lives in core:navigation.
 */
class MoviesEntryProvider : FeatureEntryProvider {
    override fun EntryProviderScope<NavKey>.install(backStack: NavBackStack<NavKey>) {
        entry<MoviesList> {
            MoviesListRoute(onOpenDetails = { id -> backStack.add(MovieDetails(id)) })
        }
    }
}
