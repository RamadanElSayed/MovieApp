package com.app.movieapp.feature.movieslist.presentation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MoviesListReducerTest {
    private val reducer = MoviesListReducer()
    private val initial = MoviesListState()

    @Test
    fun `refresh sets refreshing and clears fatal error`() {
        val start = initial.copy(fatalError = "boom")
        val next = reducer.reduce(start, MoviesListIntent.Refresh)

        assertTrue(next.isRefreshing)
        assertNull(next.fatalError)
    }

    @Test
    fun `refresh finished clears refreshing`() {
        val start = initial.copy(isRefreshing = true)
        val next = reducer.reduce(start, MoviesListIntent.RefreshFinished(errorMessage = null))

        assertFalse(next.isRefreshing)
    }

    @Test
    fun `connectivity change toggles offline flag`() {
        val next = reducer.reduce(initial, MoviesListIntent.ConnectivityChanged(isOffline = true))
        assertTrue(next.isOffline)
    }

    @Test
    fun `open details does not change state`() {
        val next = reducer.reduce(initial, MoviesListIntent.OpenDetails(1))
        assertEquals(initial, next)
    }
}
