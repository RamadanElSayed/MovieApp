package com.app.movieapp.core.database.entity

import androidx.room.Entity

/**
 * Room entity — the single source of truth for movies. Shape matches storage, not API or UI.
 *
 * The primary key is composite `(id, category)` so the SAME movie can live in several category
 * caches at once (e.g. both Popular and Top Rated) without collisions.
 */
@Entity(tableName = "movies", primaryKeys = ["id", "category"])
data class MovieEntity(
    val id: Int,
    /** Which list this row belongs to (a category key, or "DETAIL" for ad-hoc single fetches). */
    val category: String,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val releaseDate: String,
    val popularity: Double,
    /** Ordering key so the UI can page the cache in the same order the API returned it. */
    val page: Int,
    val positionInPage: Int,
)
