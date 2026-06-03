package com.app.movieapp.feature.movieslist.data.mapper

import com.app.movieapp.core.contract.movies.MovieSummary
import com.app.movieapp.core.database.entity.MovieEntity
import com.app.movieapp.core.network.NetworkConstants
import com.app.movieapp.feature.movieslist.data.remote.dto.MovieDto
import com.app.movieapp.feature.movieslist.domain.model.Movie

/**
 * Dedicated, one-directional mappers — one hop each, each independently unit-tested.
 *   DTO --(network->db)--> Entity --(db->domain)--> Movie --(domain->contract)--> MovieSummary
 */

/** Network -> DB. [category] partitions the cache; [page]/[position] preserve API ordering. */
fun MovieDto.toEntity(category: String, page: Int, position: Int): MovieEntity = MovieEntity(
    id = id,
    category = category,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    voteAverage = voteAverage,
    releaseDate = releaseDate,
    popularity = popularity,
    page = page,
    positionInPage = position,
)

/** DB -> Domain. Builds fully-qualified image URLs so the domain is UI-agnostic but ready to use. */
fun MovieEntity.toDomain(): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    posterUrl = NetworkConstants.imageUrl(posterPath, size = "w500"),
    backdropUrl = NetworkConstants.imageUrl(backdropPath, size = "w780"),
    rating = voteAverage,
    releaseDate = releaseDate,
    popularity = popularity,
)

/** Domain -> Contract (cross-feature shared model). */
fun Movie.toSummary(): MovieSummary = MovieSummary(
    id = id,
    title = title,
    posterUrl = posterUrl,
    rating = rating,
    overview = overview,
    backdropUrl = backdropUrl,
    releaseDate = releaseDate,
)
