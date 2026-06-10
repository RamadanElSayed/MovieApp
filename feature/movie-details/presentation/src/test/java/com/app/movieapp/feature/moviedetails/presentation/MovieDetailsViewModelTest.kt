package com.app.movieapp.feature.moviedetails.presentation

import com.app.movieapp.common.domain.error.AppError
import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.common.presentation.resource.ResourceProvider
import com.app.movieapp.core.contract.favorites.FavoritesProvider
import com.app.movieapp.core.contract.movies.MovieProvider
import com.app.movieapp.core.contract.movies.MovieSummary
import com.app.movieapp.feature.moviedetails.domain.usecase.GetMovieDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTest {
    private class FakeMovieProvider(private val result: Outcome<MovieSummary>) : MovieProvider {
        override suspend fun getMovie(id: Int): Outcome<MovieSummary> = result
        override fun observeMovie(id: Int): Flow<MovieSummary?> = flowOf(null)
    }

    private class FakeFavorites(private val ids: Set<Int>) : FavoritesProvider {
        override fun observeFavoriteIds(): Flow<Set<Int>> = flowOf(ids)
        override suspend fun toggleFavorite(movieId: Int) = Unit
    }

    private class FakeResources : ResourceProvider {
        override fun getString(resId: Int): String = "error"
        override fun getString(resId: Int, vararg formatArgs: Any): String = "error"
    }

    private val summary = MovieSummary(1, "Title", "poster", 8.0)

    @AfterEach
    fun tearDown() = Dispatchers.resetMain()

    private fun viewModel(result: Outcome<MovieSummary>, ids: Set<Int> = emptySet()) =
        MovieDetailsViewModel(
            movieId = 1,
            getMovieDetails = GetMovieDetailsUseCase(FakeMovieProvider(result)),
            favoritesProvider = FakeFavorites(ids),
            resources = FakeResources(),
        )

    @Test
    fun `successful load exposes the movie and stops loading`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val vm = viewModel(Outcome.Success(summary))
        advanceUntilIdle()

        assertNotNull(vm.state.value.movie)
        assertFalse(vm.state.value.isLoading)
        assertNull(vm.state.value.error)
    }

    @Test
    fun `failed load surfaces an error and no movie`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val vm = viewModel(Outcome.Failure(AppError.NotFound))
        advanceUntilIdle()

        assertEquals("error", vm.state.value.error)
        assertNull(vm.state.value.movie)
    }

    @Test
    fun `favourite status reflects the favorites provider`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val vm = viewModel(Outcome.Success(summary), ids = setOf(1))
        advanceUntilIdle()

        assertTrue(vm.state.value.isFavorite)
    }
}
