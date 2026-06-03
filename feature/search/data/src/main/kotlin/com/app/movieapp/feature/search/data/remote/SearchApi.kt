package com.app.movieapp.feature.search.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchMovieDto(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String = "",
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("vote_average") val voteAverage: Double = 0.0,
)

@Serializable
data class SearchResponseDto(
    @SerialName("results") val results: List<SearchMovieDto> = emptyList(),
)

class SearchApi(private val client: HttpClient) {
    suspend fun search(query: String): SearchResponseDto =
        client.get("search/movie") { parameter("query", query) }.body()
}
