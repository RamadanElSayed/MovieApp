package com.app.movieapp.feature.movieslist.domain.usecase

import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.common.domain.usecase.UseCase
import com.app.movieapp.feature.movieslist.domain.model.MovieCategory
import com.app.movieapp.feature.movieslist.domain.repository.MoviesRepository

class RefreshMoviesUseCase(
    private val repository: MoviesRepository,
) : UseCase<MovieCategory, Outcome<Unit>> {
    override suspend fun invoke(params: MovieCategory): Outcome<Unit> = repository.refresh(params)
}
