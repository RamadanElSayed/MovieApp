package com.app.movieapp.feature.search.data.repository

import com.app.movieapp.common.data.error.safeApiCall
import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.common.domain.result.map
import com.app.movieapp.feature.search.data.mapper.toDomain
import com.app.movieapp.feature.search.data.remote.SearchApi
import com.app.movieapp.feature.search.domain.model.SearchMovie
import com.app.movieapp.feature.search.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val api: SearchApi,
) : SearchRepository {
    override suspend fun searchMovies(query: String): Outcome<List<SearchMovie>> =
        safeApiCall { api.search(query) }.map { response -> response.results.map { it.toDomain() } }
}
