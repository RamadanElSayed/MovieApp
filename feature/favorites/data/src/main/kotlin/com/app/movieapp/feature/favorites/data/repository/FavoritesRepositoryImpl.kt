package com.app.movieapp.feature.favorites.data.repository

import android.util.Log
import com.app.movieapp.common.data.error.safeDbCall
import com.app.movieapp.common.domain.result.onFailure
import com.app.movieapp.core.database.dao.FavoriteMovieDao
import com.app.movieapp.core.database.entity.FavoriteMovieEntity
import com.app.movieapp.feature.favorites.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val dao: FavoriteMovieDao,
    private val now: () -> Long = { System.currentTimeMillis() },
) : FavoritesRepository {
    override fun observeFavoriteIds(): Flow<Set<Int>> =
        dao.observeFavoriteIds().map { it.toSet() }

    override suspend fun isFavorite(movieId: Int): Boolean = dao.isFavorite(movieId)

    override suspend fun toggleFavorite(movieId: Int) {
        safeDbCall {
            if (dao.isFavorite(movieId)) {
                dao.remove(movieId)
            } else {
                dao.add(FavoriteMovieEntity(movieId = movieId, favoritedAt = now(), pendingSync = true))
            }
        }.onFailure { error ->
            Log.w(TAG, "Failed to toggle favourite for movie $movieId: $error")
        }
    }

    private companion object {
        const val TAG = "FavoritesRepository"
    }
}
