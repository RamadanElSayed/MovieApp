package com.app.movieapp.feature.movieslist.domain.repository

import androidx.paging.PagingData
import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.feature.movieslist.domain.model.Movie
import com.app.movieapp.feature.movieslist.domain.model.MovieCategory
import kotlinx.coroutines.flow.Flow

/**
 * Repository contract (implemented in the data layer). Offline-first:
 *  - reads ([pagedMovies]/[observeMovie]) always come FROM Room (the single source of truth),
 *  - the network only refreshes the cache; its result never reaches the UI directly.
 */
interface MoviesRepository {

    /** Paged movies for [category]. Pages are served from Room; RemoteMediator refills the cache. */
    fun pagedMovies(category: MovieCategory): Flow<PagingData<Movie>>

    /** Reactive single-movie read from cache. */
    fun observeMovie(id: Int): Flow<Movie?>

    /** One-shot read (cache-first, refreshing if missing). */
    suspend fun getMovie(id: Int): Outcome<Movie>

    /** Explicit refresh of a [category] (e.g. retry). Soft-fails: never wipes good cache on error. */
    suspend fun refresh(category: MovieCategory): Outcome<Unit>
}
