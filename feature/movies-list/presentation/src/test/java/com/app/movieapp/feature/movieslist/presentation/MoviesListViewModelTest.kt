package com.app.movieapp.feature.movieslist.presentation

import androidx.paging.PagingData
import app.cash.turbine.test
import com.app.movieapp.common.domain.connectivity.ConnectivityObserver
import com.app.movieapp.common.domain.connectivity.NetworkStatus
import com.app.movieapp.common.domain.error.AppError
import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.common.presentation.resource.ResourceProvider
import com.app.movieapp.core.contract.favorites.FavoritesProvider
import com.app.movieapp.feature.movieslist.domain.model.Movie
import com.app.movieapp.feature.movieslist.domain.model.MovieCategory
import com.app.movieapp.feature.movieslist.domain.repository.MoviesRepository
import com.app.movieapp.feature.movieslist.domain.usecase.ObservePagedMoviesUseCase
import com.app.movieapp.feature.movieslist.domain.usecase.RefreshMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesListViewModelTest {
    private class FakeMoviesRepository(private val refreshResult: Outcome<Unit>) : MoviesRepository {
        override fun pagedMovies(category: MovieCategory): Flow<PagingData<Movie>> =
            flowOf(PagingData.empty())

        override fun observeMovie(id: Int): Flow<Movie?> = flowOf(null)
        override suspend fun getMovie(id: Int): Outcome<Movie> = Outcome.Failure(AppError.NotFound)
        override suspend fun refresh(category: MovieCategory): Outcome<Unit> = refreshResult
    }

    private class FakeFavorites : FavoritesProvider {
        override fun observeFavoriteIds(): Flow<Set<Int>> = flowOf(emptySet())
        override suspend fun toggleFavorite(movieId: Int) = Unit
    }

    private class FakeConnectivity(private val flow: MutableStateFlow<NetworkStatus>) : ConnectivityObserver {
        override val status: Flow<NetworkStatus> = flow
        override fun isCurrentlyAvailable(): Boolean = flow.value == NetworkStatus.AVAILABLE
    }

    private class FakeResources : ResourceProvider {
        override fun getString(resId: Int): String = "error"
        override fun getString(resId: Int, vararg formatArgs: Any): String = "error"
    }

    @AfterEach
    fun tearDown() = Dispatchers.resetMain()

    private fun viewModel(
        refreshResult: Outcome<Unit>,
        connectivity: MutableStateFlow<NetworkStatus> = MutableStateFlow(NetworkStatus.AVAILABLE),
    ): MoviesListViewModel {
        val repo = FakeMoviesRepository(refreshResult)
        return MoviesListViewModel(
            observePagedMovies = ObservePagedMoviesUseCase(repo),
            refreshMovies = RefreshMoviesUseCase(repo),
            favoritesProvider = FakeFavorites(),
            connectivityObserver = FakeConnectivity(connectivity),
            resources = FakeResources(),
        )
    }

    @Test
    fun `refresh failure emits a snackbar effect`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val vm = viewModel(Outcome.Failure(AppError.Network))

        vm.effect.test {
            vm.sendIntent(MoviesListIntent.Refresh)
            advanceUntilIdle()
            assertTrue(awaitItem() is MoviesListEffect.ShowSnackbar)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `losing connectivity marks the state offline`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val connectivity = MutableStateFlow(NetworkStatus.AVAILABLE)
        val vm = viewModel(Outcome.Success(Unit), connectivity)
        advanceUntilIdle()

        connectivity.value = NetworkStatus.UNAVAILABLE
        advanceUntilIdle()

        assertTrue(vm.state.value.isOffline)
    }
}
