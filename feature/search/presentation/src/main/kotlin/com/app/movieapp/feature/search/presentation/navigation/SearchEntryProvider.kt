package com.app.movieapp.feature.search.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.app.movieapp.core.navigation.FeatureEntryProvider
import com.app.movieapp.core.navigation.MovieDetails
import com.app.movieapp.core.navigation.Search
import com.app.movieapp.feature.search.presentation.SearchRoute

class SearchEntryProvider : FeatureEntryProvider {
    override fun EntryProviderScope<NavKey>.install(backStack: NavBackStack<NavKey>) {
        entry<Search> {
            SearchRoute(onOpenDetails = { id -> backStack.add(MovieDetails(id)) })
        }
    }
}
