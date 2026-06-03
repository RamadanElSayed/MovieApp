package com.app.movieapp.core.contract.movies

import com.app.movieapp.common.domain.result.Outcome
import kotlinx.coroutines.flow.Flow

/**
 * Cross-feature, minimal movie model. This is the ONLY movie shape that crosses a feature boundary.
 * Each feature still owns its richer internal domain model; the owning feature maps domain -> this.
 */
data class MovieSummary(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val rating: Double,
    /** Richer fields (default-empty so existing producers/consumers stay source-compatible). */
    val overview: String = "",
    val backdropUrl: String = "",
    val releaseDate: String = "",
)

/**
 * Contract OWNED & IMPLEMENTED by `feature:movies-list` and CONSUMED by other features
 * (e.g. `feature:favorites`, `feature:movie-details`) without any feature-to-feature dependency.
 *
 * `feature:movies-list → core:contract ← feature:favorites`
 */
interface MovieProvider {
    /** One-shot fetch of a single movie (offline-first: served from cache when available). */
    suspend fun getMovie(id: Int): Outcome<MovieSummary>

    /** Observe a movie reactively from the single source of truth (Room). */
    fun observeMovie(id: Int): Flow<MovieSummary?>
}
