package com.app.movieapp.feature.moviedetails.domain.usecase

import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.common.domain.usecase.UseCase
import com.app.movieapp.core.contract.movies.MovieProvider
import com.app.movieapp.core.contract.movies.MovieSummary

/**
 * Fetches a movie purely through the [MovieProvider] contract — movie-details never depends on the
 * movies-list module that implements it.
 */
class GetMovieDetailsUseCase(
    private val movieProvider: MovieProvider,
) : UseCase<Int, Outcome<MovieSummary>> {
    override suspend fun invoke(params: Int): Outcome<MovieSummary> = movieProvider.getMovie(params)
}
