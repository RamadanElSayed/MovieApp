package com.app.movieapp.feature.search.domain.usecase

import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.feature.search.domain.model.SearchMovie
import com.app.movieapp.feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SearchMoviesUseCaseTest {
    private class FakeRepo(
        val result: Outcome<List<SearchMovie>>,
    ) : SearchRepository {
        var called = false

        override suspend fun searchMovies(query: String): Outcome<List<SearchMovie>> {
            called = true
            return result
        }
    }

    @Test
    fun `blank query short-circuits without hitting the repository`() =
        runTest {
            val repo = FakeRepo(Outcome.Success(emptyList()))
            val useCase = SearchMoviesUseCase(repo)

            val result = useCase("   ")

            assertEquals(Outcome.Success(emptyList<SearchMovie>()), result)
            assertTrue(!repo.called, "repository must not be called for blank query")
        }

    @Test
    fun `non-blank query delegates to repository`() =
        runTest {
            val expected = listOf(SearchMovie(1, "A", "", 7.0))
            val repo = FakeRepo(Outcome.Success(expected))
            val useCase = SearchMoviesUseCase(repo)

            val result = useCase("matrix")

            assertEquals(Outcome.Success(expected), result)
            assertTrue(repo.called)
        }
}
