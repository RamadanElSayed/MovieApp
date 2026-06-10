package com.app.movieapp.feature.search.presentation

import com.app.movieapp.feature.search.presentation.model.SearchResultUiModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SearchReducerTest {
    private val reducer = SearchReducer()
    private val initial = SearchState()

    private val results = listOf(
        SearchResultUiModel(id = 1, title = "Dune", posterUrl = "p", ratingLabel = "8.0"),
    )

    @Test
    fun `query changed updates query and clears error`() {
        val start = initial.copy(error = "boom")
        val next = reducer.reduce(start, SearchIntent.QueryChanged("dune"))

        assertEquals("dune", next.query)
        assertNull(next.error)
    }

    @Test
    fun `loading sets the loading flag`() {
        val next = reducer.reduce(initial, SearchIntent.Loading)
        assertTrue(next.isLoading)
    }

    @Test
    fun `results loaded populates results and stops loading`() {
        val start = initial.copy(isLoading = true)
        val next = reducer.reduce(start, SearchIntent.ResultsLoaded(results))

        assertFalse(next.isLoading)
        assertEquals(results, next.results)
    }

    @Test
    fun `failed sets the error message`() {
        val next = reducer.reduce(initial.copy(isLoading = true), SearchIntent.Failed("nope"))

        assertFalse(next.isLoading)
        assertEquals("nope", next.error)
    }

    @Test
    fun `showEmpty is true only for a non-blank query with no results`() {
        val empty = initial.copy(query = "zzz", isLoading = false, results = emptyList())
        assertTrue(empty.showEmpty)

        val blank = initial.copy(query = "", results = emptyList())
        assertFalse(blank.showEmpty)
    }
}
