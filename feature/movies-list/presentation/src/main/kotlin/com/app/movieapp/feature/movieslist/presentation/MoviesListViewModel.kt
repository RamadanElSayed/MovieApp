package com.app.movieapp.feature.movieslist.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.app.movieapp.common.domain.connectivity.ConnectivityObserver
import com.app.movieapp.common.domain.connectivity.NetworkStatus
import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.common.presentation.error.toMessage
import com.app.movieapp.common.presentation.mvi.BaseViewModel
import com.app.movieapp.common.presentation.resource.ResourceProvider
import com.app.movieapp.core.contract.favorites.FavoritesProvider
import com.app.movieapp.feature.movieslist.domain.model.MovieCategory
import com.app.movieapp.feature.movieslist.domain.usecase.ObservePagedMoviesUseCase
import com.app.movieapp.feature.movieslist.domain.usecase.RefreshMoviesUseCase
import com.app.movieapp.feature.movieslist.presentation.model.MovieUiModel
import com.app.movieapp.feature.movieslist.presentation.model.toUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class MoviesListViewModel(
    observePagedMovies: ObservePagedMoviesUseCase,
    private val refreshMovies: RefreshMoviesUseCase,
    private val favoritesProvider: FavoritesProvider,
    private val connectivityObserver: ConnectivityObserver,
    private val resources: ResourceProvider,
) : BaseViewModel<MoviesListState, MoviesListIntent, MoviesListEffect>(
    initialState = MoviesListState(),
    reducer = MoviesListReducer(),
) {
    private val selectedCategory = MutableStateFlow(MovieCategory.POPULAR)

    val pagedMovies: Flow<PagingData<MovieUiModel>> =
        selectedCategory
            .flatMapLatest { category -> observePagedMovies(category) }
            .cachedIn(viewModelScope)
            .combine(favoritesProvider.observeFavoriteIds()) { paging, favIds ->
                paging.map { movie -> movie.toUi(isFavorite = movie.id in favIds) }
            }

    init {
        observeConnectivity()
    }

    override fun handleIntent(intent: MoviesListIntent) {
        when (intent) {
            MoviesListIntent.Load -> Unit
            MoviesListIntent.Refresh, MoviesListIntent.Retry -> refresh()
            is MoviesListIntent.SelectCategory -> selectedCategory.value = intent.category
            is MoviesListIntent.OpenDetails ->
                sendEffect(MoviesListEffect.NavigateToDetails(intent.movieId))
            is MoviesListIntent.ToggleFavorite -> toggleFavorite(intent.movieId)
            is MoviesListIntent.ConnectivityChanged,
            is MoviesListIntent.RefreshFinished,
            -> Unit
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            when (val result = refreshMovies(selectedCategory.value)) {
                is Outcome.Success -> sendIntent(MoviesListIntent.RefreshFinished(null))
                is Outcome.Failure -> {
                    sendIntent(MoviesListIntent.RefreshFinished(null))
                    sendEffect(MoviesListEffect.ShowSnackbar(result.error.toMessage(resources)))
                }
            }
        }
    }

    private fun toggleFavorite(movieId: Int) {
        viewModelScope.launch { favoritesProvider.toggleFavorite(movieId) }
    }

    private fun observeConnectivity() {
        viewModelScope.launch {
            connectivityObserver.status.collect { status ->
                sendIntent(MoviesListIntent.ConnectivityChanged(status == NetworkStatus.UNAVAILABLE))
            }
        }
    }
}
