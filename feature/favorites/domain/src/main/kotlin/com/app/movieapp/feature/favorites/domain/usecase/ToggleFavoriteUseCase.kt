package com.app.movieapp.feature.favorites.domain.usecase

import com.app.movieapp.common.domain.usecase.UseCase
import com.app.movieapp.feature.favorites.domain.repository.FavoritesRepository

class ToggleFavoriteUseCase(
    private val repository: FavoritesRepository,
) : UseCase<Int, Unit> {
    override suspend fun invoke(params: Int) = repository.toggleFavorite(params)
}
