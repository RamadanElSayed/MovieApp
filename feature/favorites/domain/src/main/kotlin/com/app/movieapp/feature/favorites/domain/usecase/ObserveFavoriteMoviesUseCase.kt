package com.app.movieapp.feature.favorites.domain.usecase

import com.app.movieapp.common.domain.usecase.FlowUseCase
import com.app.movieapp.core.contract.movies.MovieProvider
import com.app.movieapp.core.contract.movies.MovieSummary
import com.app.movieapp.feature.favorites.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class ObserveFavoriteMoviesUseCase(
    private val favoritesRepository: FavoritesRepository,
    private val movieProvider: MovieProvider,
) : FlowUseCase<Unit, List<MovieSummary>> {
    override fun invoke(params: Unit): Flow<List<MovieSummary>> =
        favoritesRepository.observeFavoriteIds().flatMapLatest { ids ->
            if (ids.isEmpty()) {
                flowOf(emptyList())
            } else {
                combine(ids.map { id -> movieProvider.observeMovie(id) }) { movies ->
                    movies.filterNotNull()
                }
            }
        }
}
