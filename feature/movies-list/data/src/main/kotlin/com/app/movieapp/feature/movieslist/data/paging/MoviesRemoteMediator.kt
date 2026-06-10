package com.app.movieapp.feature.movieslist.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.app.movieapp.common.data.error.toAppError
import com.app.movieapp.common.domain.error.AppErrorException
import com.app.movieapp.core.database.MovieAppDatabase
import com.app.movieapp.core.database.entity.MovieEntity
import com.app.movieapp.core.database.entity.MovieRemoteKeyEntity
import com.app.movieapp.feature.movieslist.data.mapper.toEntity
import com.app.movieapp.feature.movieslist.data.remote.MoviesApi
import com.app.movieapp.feature.movieslist.domain.model.MovieCategory
import kotlinx.coroutines.CancellationException

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val category: MovieCategory,
    private val api: MoviesApi,
    private val database: MovieAppDatabase,
    private val now: () -> Long = { System.currentTimeMillis() },
) : RemoteMediator<Int, MovieEntity>() {
    private val movieDao = database.movieDao()
    private val keyDao = database.movieRemoteKeyDao()
    private val categoryKey = category.name

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>,
    ): MediatorResult {
        return try {
            val page: Int = when (loadType) {
                LoadType.REFRESH -> FIRST_PAGE

                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val last = keyDao.latest(categoryKey)
                    last?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val response = api.getCategory(category.apiPath, page)
            val movies = response.results
            val endReached = page >= response.totalPages

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.clearCategory(categoryKey)
                    keyDao.clearCategory(categoryKey)
                }
                val prevKey = if (page == FIRST_PAGE) null else page - 1
                val nextKey = if (endReached) null else page + 1
                val timestamp = now()

                val entities = movies.mapIndexed { index, dto ->
                    dto.toEntity(categoryKey, page, index)
                }
                movieDao.upsertAll(entities)
                keyDao.upsertAll(
                    entities.map { MovieRemoteKeyEntity(it.id, categoryKey, prevKey, nextKey, timestamp) },
                )
            }

            MediatorResult.Success(endOfPaginationReached = endReached)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            MediatorResult.Error(AppErrorException(e.toAppError(), cause = e))
        }
    }

    private companion object {
        const val FIRST_PAGE = 1
    }
}
