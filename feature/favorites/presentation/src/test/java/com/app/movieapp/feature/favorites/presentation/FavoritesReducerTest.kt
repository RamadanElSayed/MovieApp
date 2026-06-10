package com.app.movieapp.feature.favorites.presentation

import com.app.movieapp.feature.favorites.presentation.model.FavoriteUiModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FavoritesReducerTest {
    private val reducer = FavoritesReducer()
    private val initial = FavoritesState()

    private val items = listOf(
        FavoriteUiModel(id = 1, title = "Heat", posterUrl = "p", ratingLabel = "8.3"),
    )

    @Test
    fun `load sets the loading flag`() {
        val next = reducer.reduce(initial.copy(isLoading = false), FavoritesIntent.Load)
        assertTrue(next.isLoading)
    }

    @Test
    fun `items loaded populates items and stops loading`() {
        val next = reducer.reduce(initial, FavoritesIntent.ItemsLoaded(items))

        assertFalse(next.isLoading)
        assertEquals(items, next.items)
    }

    @Test
    fun `empty is true only after loading finishes with no items`() {
        val loaded = reducer.reduce(initial, FavoritesIntent.ItemsLoaded(emptyList()))
        assertTrue(loaded.isEmpty)
        assertFalse(initial.isEmpty)
    }

    @Test
    fun `remove and open details do not change state`() {
        assertEquals(initial, reducer.reduce(initial, FavoritesIntent.Remove(1)))
        assertEquals(initial, reducer.reduce(initial, FavoritesIntent.OpenDetails(1)))
    }
}
