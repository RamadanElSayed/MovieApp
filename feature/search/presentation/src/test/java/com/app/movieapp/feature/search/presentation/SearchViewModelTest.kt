package com.app.movieapp.feature.search.presentation

import com.app.movieapp.common.domain.error.AppError
import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.common.presentation.resource.ResourceProvider
import com.app.movieapp.feature.search.domain.model.SearchMovie
import com.app.movieapp.feature.search.domain.repository.SearchRepository
import com.app.movieapp.feature.search.domain.usecase.SearchMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    private class FakeRepo(private val result: Outcome<List<SearchMovie>>) : SearchRepository {
        override suspend fun searchMovies(query: String): Outcome<List<SearchMovie>> = result
    }

    private class FakeResources : ResourceProvider {
        override fun getString(resId: Int): String = "error"
        override fun getString(resId: Int, vararg formatArgs: Any): String = "error"
    }

    @AfterEach
    fun tearDown() = Dispatchers.resetMain()

    private fun viewModel(result: Outcome<List<SearchMovie>>) =
        SearchViewModel(SearchMoviesUseCase(FakeRepo(result)), FakeResources())

    @Test
    fun `successful search populates results and clears error`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        val vm = viewModel(Outcome.Success(listOf(SearchMovie(1, "A", "", 7.0))))

        vm.sendIntent(SearchIntent.QueryChanged("matrix"))
        advanceUntilIdle()

        assertEquals(1, vm.state.value.results.size)
        assertNull(vm.state.value.error)
        assertFalse(vm.state.value.isLoading)
    }

    @Test
    fun `failed search surfaces a localized error message`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        val vm = viewModel(Outcome.Failure(AppError.Network))

        vm.sendIntent(SearchIntent.QueryChanged("matrix"))
        advanceUntilIdle()

        assertEquals("error", vm.state.value.error)
        assertTrue(vm.state.value.results.isEmpty())
    }

    @Test
    fun `blank query yields empty results without loading`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        val vm = viewModel(Outcome.Success(listOf(SearchMovie(1, "A", "", 7.0))))

        vm.sendIntent(SearchIntent.QueryChanged("   "))
        advanceUntilIdle()

        assertTrue(vm.state.value.results.isEmpty())
        assertFalse(vm.state.value.isLoading)
    }
}
