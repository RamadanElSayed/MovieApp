package com.app.movieapp.feature.search.domain.repository

import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.feature.search.domain.model.SearchMovie

interface SearchRepository {
    suspend fun searchMovies(query: String): Outcome<List<SearchMovie>>
}
