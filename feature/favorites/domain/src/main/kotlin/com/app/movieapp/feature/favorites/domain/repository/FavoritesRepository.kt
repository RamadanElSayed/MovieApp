package com.app.movieapp.feature.favorites.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun observeFavoriteIds(): Flow<Set<Int>>

    suspend fun isFavorite(movieId: Int): Boolean

    suspend fun toggleFavorite(movieId: Int)
}
