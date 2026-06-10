package com.app.movieapp.feature.movieslist.domain.usecase

import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.common.domain.usecase.UseCase
import com.app.movieapp.feature.movieslist.domain.model.Movie
import com.app.movieapp.feature.movieslist.domain.repository.MoviesRepository

class GetMovieUseCase(
    private val repository: MoviesRepository,
) : UseCase<Int, Outcome<Movie>> {
    override suspend fun invoke(params: Int): Outcome<Movie> = repository.getMovie(params)
}
