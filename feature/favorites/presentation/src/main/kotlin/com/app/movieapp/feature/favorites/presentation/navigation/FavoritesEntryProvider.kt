package com.app.movieapp.feature.favorites.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.app.movieapp.core.navigation.FeatureEntryProvider
import com.app.movieapp.core.navigation.Favorites
import com.app.movieapp.core.navigation.MovieDetails
import com.app.movieapp.feature.favorites.presentation.FavoritesRoute

class FavoritesEntryProvider : FeatureEntryProvider {
    override fun EntryProviderScope<NavKey>.install(backStack: NavBackStack<NavKey>) {
        entry<Favorites> {
            FavoritesRoute(onOpenDetails = { id -> backStack.add(MovieDetails(id)) })
        }
    }
}
