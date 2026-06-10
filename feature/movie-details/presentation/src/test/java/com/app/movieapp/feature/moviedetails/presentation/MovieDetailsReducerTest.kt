package com.app.movieapp.feature.moviedetails.presentation

import com.app.movieapp.feature.moviedetails.presentation.model.MovieDetailsUiModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MovieDetailsReducerTest {
    private val reducer = MovieDetailsReducer()
    private val initial = MovieDetailsState()

    private val movie = MovieDetailsUiModel(
        id = 1,
        title = "Inception",
        posterUrl = "p",
        backdropUrl = "b",
        ratingLabel = "8.3",
        year = "2010",
        overview = "A thief...",
    )

    @Test
    fun `retry shows loading and clears error`() {
        val start = initial.copy(isLoading = false, error = "boom")
        val next = reducer.reduce(start, MovieDetailsIntent.Retry)

        assertTrue(next.isLoading)
        assertNull(next.error)
    }

    @Test
    fun `loaded populates movie and stops loading`() {
        val next = reducer.reduce(initial, MovieDetailsIntent.Loaded(movie))

        assertFalse(next.isLoading)
        assertEquals(movie, next.movie)
        assertNull(next.error)
    }

    @Test
    fun `failed sets error and stops loading`() {
        val next = reducer.reduce(initial, MovieDetailsIntent.Failed("nope"))

        assertFalse(next.isLoading)
        assertEquals("nope", next.error)
    }

    @Test
    fun `favorite changed updates only the flag`() {
        val loaded = reducer.reduce(initial, MovieDetailsIntent.Loaded(movie))
        val next = reducer.reduce(loaded, MovieDetailsIntent.FavoriteChanged(isFavorite = true))

        assertTrue(next.isFavorite)
        assertEquals(movie, next.movie)
    }

    @Test
    fun `toggle favorite is a no-op in the reducer`() {
        val next = reducer.reduce(initial, MovieDetailsIntent.ToggleFavorite)
        assertEquals(initial, next)
    }
}
