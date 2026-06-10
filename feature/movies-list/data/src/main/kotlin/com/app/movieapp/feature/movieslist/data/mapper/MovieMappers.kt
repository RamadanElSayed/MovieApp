package com.app.movieapp.feature.movieslist.data.mapper

import com.app.movieapp.core.contract.movies.MovieSummary
import com.app.movieapp.core.database.entity.MovieEntity
import com.app.movieapp.core.network.NetworkConstants
import com.app.movieapp.feature.movieslist.data.remote.dto.MovieDto
import com.app.movieapp.feature.movieslist.domain.model.Movie

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

fun Movie.toSummary(): MovieSummary = MovieSummary(
    id = id,
    title = title,
    posterUrl = posterUrl,
    rating = rating,
    overview = overview,
    backdropUrl = backdropUrl,
    releaseDate = releaseDate,
)
