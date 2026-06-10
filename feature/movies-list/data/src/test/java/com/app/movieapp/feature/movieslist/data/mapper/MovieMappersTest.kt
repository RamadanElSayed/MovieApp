package com.app.movieapp.feature.movieslist.data.mapper

import com.app.movieapp.feature.movieslist.data.remote.dto.MovieDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MovieMappersTest {
    private val dto = MovieDto(
        id = 42,
        title = "Inception",
        overview = "A thief who steals corporate secrets.",
        posterPath = "/poster.jpg",
        backdropPath = "/backdrop.jpg",
        voteAverage = 8.3,
        releaseDate = "2010-07-16",
        popularity = 99.5,
    )

    @Test
    fun `dto maps to entity preserving category, page and position`() {
        val entity = dto.toEntity(category = "POPULAR", page = 2, position = 5)

        assertEquals(42, entity.id)
        assertEquals("POPULAR", entity.category)
        assertEquals("Inception", entity.title)
        assertEquals("/poster.jpg", entity.posterPath)
        assertEquals(2, entity.page)
        assertEquals(5, entity.positionInPage)
    }

    @Test
    fun `entity maps to domain building a full poster url`() {
        val movie = dto.toEntity("POPULAR", 0, 0).toDomain()

        assertEquals(42, movie.id)
        assertEquals(8.3, movie.rating, 0.0001)
        assertTrue(movie.posterUrl.endsWith("/poster.jpg"))
        assertTrue(movie.backdropUrl.endsWith("/backdrop.jpg"))
    }

    @Test
    fun `domain maps to contract summary`() {
        val summary = dto.toEntity("POPULAR", 0, 0).toDomain().toSummary()

        assertEquals(42, summary.id)
        assertEquals("Inception", summary.title)
        assertEquals(8.3, summary.rating, 0.0001)
    }

    @Test
    fun `null poster path yields empty url`() {
        val movie = dto.copy(posterPath = null).toEntity("POPULAR", 0, 0).toDomain()
        assertEquals("", movie.posterUrl)
    }
}
