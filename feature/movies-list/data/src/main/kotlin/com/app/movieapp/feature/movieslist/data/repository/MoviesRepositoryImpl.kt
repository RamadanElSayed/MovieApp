package com.app.movieapp.feature.movieslist.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.app.movieapp.common.data.error.safeApiCall
import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.common.domain.result.map
import com.app.movieapp.core.database.MovieAppDatabase
import com.app.movieapp.feature.movieslist.data.mapper.toDomain
import com.app.movieapp.feature.movieslist.data.mapper.toEntity
import com.app.movieapp.feature.movieslist.data.paging.MoviesRemoteMediator
import com.app.movieapp.feature.movieslist.data.remote.MoviesApi
import com.app.movieapp.feature.movieslist.domain.model.Movie
import com.app.movieapp.feature.movieslist.domain.model.MovieCategory
import com.app.movieapp.feature.movieslist.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Offline-first repository. Room is the single source of truth:
 *  - [pagedMovies] pages FROM Room, with [MoviesRemoteMediator] refilling the cache from the API.
 *  - [observeMovie] reads reactively FROM Room.
 *  - the network result is mapped DTO->Entity and written THROUGH to Room; it never reaches the UI.
 */
@OptIn(ExperimentalPagingApi::class)
class MoviesRepositoryImpl(
    private val api: MoviesApi,
    private val database: MovieAppDatabase,
) : MoviesRepository {

    private val movieDao = database.movieDao()

    override fun pagedMovies(category: MovieCategory): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
        remoteMediator = MoviesRemoteMediator(category, api, database),
        pagingSourceFactory = { movieDao.pagingSource(category.name) },
    ).flow.map { paging -> paging.map { it.toDomain() } }

    override fun observeMovie(id: Int): Flow<Movie?> =
        movieDao.observeById(id).map { it?.toDomain() }

    override suspend fun getMovie(id: Int): Outcome<Movie> {
        movieDao.findById(id)?.let { return Outcome.Success(it.toDomain()) }
        // Not cached -> fetch, write through (under the DETAIL bucket so it doesn't pollute any
        // list), then read back from the source of truth.
        return safeApiCall { api.getMovie(id) }.map { dto ->
            val entity = dto.toEntity(category = DETAIL_CATEGORY, page = 0, position = 0)
            movieDao.upsertAll(listOf(entity))
            entity.toDomain()
        }
    }

    override suspend fun refresh(category: MovieCategory): Outcome<Unit> = safeApiCall {
        val response = api.getCategory(category.apiPath, 1)
        val entities = response.results.mapIndexed { i, dto -> dto.toEntity(category.name, 1, i) }
        // Write-through refresh; existing cache stays intact if the call throws (soft failure).
        movieDao.upsertAll(entities)
    }

    private companion object {
        const val PAGE_SIZE = 20
        const val DETAIL_CATEGORY = "DETAIL"
    }
}
