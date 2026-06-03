package com.app.movieapp.core.contract.favorites

import kotlinx.coroutines.flow.Flow

/**
 * Contract OWNED & IMPLEMENTED by `feature:favorites` and consumed by e.g. `feature:movies-list`
 * (to render a filled/empty heart) without depending on the favorites feature.
 */
interface FavoritesProvider {
    /** Reactive set of favourited movie ids — the single source of truth is Room. */
    fun observeFavoriteIds(): Flow<Set<Int>>

    /** Optimistic toggle: writes to Room immediately, then syncs to the backend. */
    suspend fun toggleFavorite(movieId: Int)
}
