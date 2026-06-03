package com.app.movieapp.feature.moviedetails.presentation

import androidx.lifecycle.viewModelScope
import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.common.presentation.error.toMessage
import com.app.movieapp.common.presentation.mvi.BaseViewModel
import com.app.movieapp.common.presentation.resource.ResourceProvider
import com.app.movieapp.core.contract.favorites.FavoritesProvider
import com.app.movieapp.feature.moviedetails.domain.usecase.GetMovieDetailsUseCase
import com.app.movieapp.feature.moviedetails.presentation.model.toUi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieId: Int,
    private val getMovieDetails: GetMovieDetailsUseCase,
    private val favoritesProvider: FavoritesProvider,
    private val resources: ResourceProvider,
) : BaseViewModel<MovieDetailsState, MovieDetailsIntent, MovieDetailsEffect>(
    initialState = MovieDetailsState(),
    reducer = MovieDetailsReducer(),
) {
    init {
        sendIntent(MovieDetailsIntent.Load)
        observeFavorite()
    }

    override fun handleIntent(intent: MovieDetailsIntent) {
        when (intent) {
            MovieDetailsIntent.Load, MovieDetailsIntent.Retry -> load()
            MovieDetailsIntent.ToggleFavorite ->
                viewModelScope.launch { favoritesProvider.toggleFavorite(movieId) }
            is MovieDetailsIntent.Loaded,
            is MovieDetailsIntent.Failed,
            is MovieDetailsIntent.FavoriteChanged,
            -> Unit
        }
    }

    private fun load() {
        viewModelScope.launch {
            when (val result = getMovieDetails(movieId)) {
                is Outcome.Success -> sendIntent(MovieDetailsIntent.Loaded(result.data.toUi()))
                is Outcome.Failure ->
                    sendIntent(MovieDetailsIntent.Failed(result.error.toMessage(resources)))
            }
        }
    }

    private fun observeFavorite() {
        viewModelScope.launch {
            favoritesProvider.observeFavoriteIds()
                .map { movieId in it }
                .collect { sendIntent(MovieDetailsIntent.FavoriteChanged(it)) }
        }
    }
}
