package com.app.movieapp.feature.movieslist.data.repository

import com.app.movieapp.common.domain.error.AppError
import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.core.database.MovieAppDatabase
import com.app.movieapp.core.database.dao.MovieDao
import com.app.movieapp.core.database.entity.MovieEntity
import com.app.movieapp.feature.movieslist.data.remote.MoviesApi
import com.app.movieapp.feature.movieslist.data.remote.dto.MovieDto
import com.app.movieapp.feature.movieslist.domain.model.MovieCategory
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MoviesRepositoryImplTest {
    private val dao = mockk<MovieDao>(relaxed = true)
    private val database = mockk<MovieAppDatabase> { every { movieDao() } returns dao }
    private val api = mockk<MoviesApi>()
    private val repository = MoviesRepositoryImpl(api, database)

    private fun entity(id: Int) =
        MovieEntity(id, "DETAIL", "T$id", "O", null, null, 8.0, "2020", 1.0, 0, 0)

    @Test
    fun `getMovie returns cached movie without hitting the network`() = runTest {
        coEvery { dao.findById(1) } returns entity(1)

        val result = repository.getMovie(1)

        assertTrue(result is Outcome.Success)
        coVerify(exactly = 0) { api.getMovie(any()) }
    }

    @Test
    fun `getMovie falls back to network and caches on a miss`() = runTest {
        coEvery { dao.findById(2) } returns null
        coEvery { api.getMovie(2) } returns MovieDto(id = 2, title = "Fetched")
        coEvery { dao.upsertAll(any()) } just Runs

        val result = repository.getMovie(2)

        assertTrue(result is Outcome.Success)
        assertEquals("Fetched", (result as Outcome.Success).data.title)
        coVerify { dao.upsertAll(any()) }
    }

    @Test
    fun `refresh soft-fails to a typed error without wiping the cache`() = runTest {
        coEvery { api.getCategory(any(), any()) } throws IOException("offline")

        val result = repository.refresh(MovieCategory.POPULAR)

        assertEquals(Outcome.Failure(AppError.Network), result)
        coVerify(exactly = 0) { dao.upsertAll(any()) }
    }
}
