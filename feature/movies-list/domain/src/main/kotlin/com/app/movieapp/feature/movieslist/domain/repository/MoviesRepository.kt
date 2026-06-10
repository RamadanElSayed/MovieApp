package com.app.movieapp.feature.movieslist.domain.repository

import androidx.paging.PagingData
import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.feature.movieslist.domain.model.Movie
import com.app.movieapp.feature.movieslist.domain.model.MovieCategory
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun pagedMovies(category: MovieCategory): Flow<PagingData<Movie>>

    fun observeMovie(id: Int): Flow<Movie?>

    suspend fun getMovie(id: Int): Outcome<Movie>

    suspend fun refresh(category: MovieCategory): Outcome<Unit>
}
