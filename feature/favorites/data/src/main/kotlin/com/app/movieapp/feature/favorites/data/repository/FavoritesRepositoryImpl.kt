package com.app.movieapp.feature.favorites.data.repository

import com.app.movieapp.core.database.dao.FavoriteMovieDao
import com.app.movieapp.core.database.entity.FavoriteMovieEntity
import com.app.movieapp.feature.favorites.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Offline-first favourites. Room is the source of truth and writes are optimistic — the UI updates
 * instantly because it observes Room; the (placeholder) backend sync runs afterwards via WorkManager
 * and reconciles `pendingSync` rows on reconnect.
 */
class FavoritesRepositoryImpl(
    private val dao: FavoriteMovieDao,
    private val now: () -> Long = { System.currentTimeMillis() },
) : FavoritesRepository {

    override fun observeFavoriteIds(): Flow<Set<Int>> =
        dao.observeFavoriteIds().map { it.toSet() }

    override suspend fun isFavorite(movieId: Int): Boolean = dao.isFavorite(movieId)

    override suspend fun toggleFavorite(movieId: Int) {
        if (dao.isFavorite(movieId)) {
            dao.remove(movieId)
        } else {
            dao.add(FavoriteMovieEntity(movieId = movieId, favoritedAt = now(), pendingSync = true))
        }
        // TODO: enqueue a WorkManager sync job to push the change to the backend and clear pendingSync.
    }
}
