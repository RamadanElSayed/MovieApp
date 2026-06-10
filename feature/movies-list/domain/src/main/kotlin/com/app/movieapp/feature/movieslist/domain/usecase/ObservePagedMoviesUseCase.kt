package com.app.movieapp.feature.movieslist.domain.usecase

import androidx.paging.PagingData
import com.app.movieapp.common.domain.usecase.FlowUseCase
import com.app.movieapp.feature.movieslist.domain.model.Movie
import com.app.movieapp.feature.movieslist.domain.model.MovieCategory
import com.app.movieapp.feature.movieslist.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class ObservePagedMoviesUseCase(
    private val repository: MoviesRepository,
) : FlowUseCase<MovieCategory, PagingData<Movie>> {
    override fun invoke(params: MovieCategory): Flow<PagingData<Movie>> = repository.pagedMovies(params)
}
