package com.app.movieapp.feature.search.domain.usecase

import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.common.domain.usecase.UseCase
import com.app.movieapp.feature.search.domain.model.SearchMovie
import com.app.movieapp.feature.search.domain.repository.SearchRepository

class SearchMoviesUseCase(
    private val repository: SearchRepository,
) : UseCase<String, Outcome<List<SearchMovie>>> {
    override suspend fun invoke(params: String): Outcome<List<SearchMovie>> =
        if (params.isBlank()) {
            Outcome.Success(emptyList())
        } else {
            repository.searchMovies(params.trim())
        }
}
