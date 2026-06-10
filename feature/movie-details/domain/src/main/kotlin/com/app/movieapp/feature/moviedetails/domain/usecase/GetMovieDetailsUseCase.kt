package com.app.movieapp.feature.moviedetails.domain.usecase

import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.common.domain.usecase.UseCase
import com.app.movieapp.core.contract.movies.MovieProvider
import com.app.movieapp.core.contract.movies.MovieSummary

class GetMovieDetailsUseCase(
    private val movieProvider: MovieProvider,
) : UseCase<Int, Outcome<MovieSummary>> {
    override suspend fun invoke(params: Int): Outcome<MovieSummary> = movieProvider.getMovie(params)
}
