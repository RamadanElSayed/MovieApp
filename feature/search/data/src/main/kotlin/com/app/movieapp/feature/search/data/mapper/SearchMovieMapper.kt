package com.app.movieapp.feature.search.data.mapper

import com.app.movieapp.core.network.NetworkConstants
import com.app.movieapp.feature.search.data.remote.SearchMovieDto
import com.app.movieapp.feature.search.domain.model.SearchMovie

fun SearchMovieDto.toDomain(): SearchMovie = SearchMovie(
    id = id,
    title = title,
    posterUrl = NetworkConstants.imageUrl(posterPath, size = "w342"),
    rating = voteAverage,
)
