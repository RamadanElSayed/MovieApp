package com.app.movieapp.feature.favorites.domain.repository

import kotlinx.coroutines.flow.Flow

/** Favourites contract (implemented in the data layer; Room is the source of truth). */
interface FavoritesRepository {
    fun observeFavoriteIds(): Flow<Set<Int>>
    suspend fun isFavorite(movieId: Int): Boolean
    /** Optimistic toggle: write to Room immediately, then reconcile with the backend. */
    suspend fun toggleFavorite(movieId: Int)
}
