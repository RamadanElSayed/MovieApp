package com.app.movieapp.feature.favorites.data.contract

import com.app.movieapp.core.contract.favorites.FavoritesProvider
import com.app.movieapp.feature.favorites.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow

/**
 * favorites OWNS and IMPLEMENTS the [FavoritesProvider] contract. movies-list consumes it (filled
 * heart) without depending on this module.
 */
internal class FavoritesProviderImpl(
    private val repository: FavoritesRepository,
) : FavoritesProvider {
    override fun observeFavoriteIds(): Flow<Set<Int>> = repository.observeFavoriteIds()
    override suspend fun toggleFavorite(movieId: Int) = repository.toggleFavorite(movieId)
}
