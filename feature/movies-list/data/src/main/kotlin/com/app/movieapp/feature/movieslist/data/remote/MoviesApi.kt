package com.app.movieapp.feature.movieslist.data.remote

import com.app.movieapp.feature.movieslist.data.remote.dto.MovieDto
import com.app.movieapp.feature.movieslist.data.remote.dto.MoviesPageDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/** Thin Ktor wrapper around the TMDB movie endpoints this feature needs. */
class MoviesApi(
    private val client: HttpClient,
) {
    /** Fetches one page of a movie list endpoint (popular / now_playing / top_rated / upcoming). */
    suspend fun getCategory(apiPath: String, page: Int): MoviesPageDto =
        client.get(apiPath) {
            parameter("page", page)
        }.body()

    suspend fun getMovie(id: Int): MovieDto =
        client.get("movie/$id").body()
}
