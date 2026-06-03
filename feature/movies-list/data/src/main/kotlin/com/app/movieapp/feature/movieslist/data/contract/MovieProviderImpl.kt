package com.app.movieapp.feature.movieslist.data.contract

import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.common.domain.result.map
import com.app.movieapp.core.contract.movies.MovieProvider
import com.app.movieapp.core.contract.movies.MovieSummary
import com.app.movieapp.feature.movieslist.data.mapper.toSummary
import com.app.movieapp.feature.movieslist.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * movies-list OWNS and IMPLEMENTS the [MovieProvider] contract. Other features (favorites,
 * movie-details) consume the interface via Koin without ever depending on this module.
 * `internal` so nothing outside this module can reference the implementation type directly.
 */
internal class MovieProviderImpl(
    private val repository: MoviesRepository,
) : MovieProvider {

    override suspend fun getMovie(id: Int): Outcome<MovieSummary> =
        repository.getMovie(id).map { it.toSummary() }

    override fun observeMovie(id: Int): Flow<MovieSummary?> =
        repository.observeMovie(id).map { it?.toSummary() }
}
