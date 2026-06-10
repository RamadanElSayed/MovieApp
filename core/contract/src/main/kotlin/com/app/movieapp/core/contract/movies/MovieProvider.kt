package com.app.movieapp.core.contract.movies

import com.app.movieapp.common.domain.result.Outcome
import kotlinx.coroutines.flow.Flow

data class MovieSummary(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val rating: Double,
    val overview: String = "",
    val backdropUrl: String = "",
    val releaseDate: String = "",
)

interface MovieProvider {
    suspend fun getMovie(id: Int): Outcome<MovieSummary>

    fun observeMovie(id: Int): Flow<MovieSummary?>
}
