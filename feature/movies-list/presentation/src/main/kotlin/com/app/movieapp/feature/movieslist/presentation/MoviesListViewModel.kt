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

/**
 * MVI ViewModel for the movies list. The paged list is exposed as a dedicated
 * `Flow<PagingData<MovieUiModel>>` (Paging owns its LoadState); screen-level concerns
 * (selected category / refresh / offline / fatal error) live in [MoviesListState].
 */
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

    /** The currently selected category — switching it re-pages from the right cache. */
    private val selectedCategory = MutableStateFlow(MovieCategory.POPULAR)

    /**
     * Paged movies for the selected category, joined with the favourites set and mapped to UI models.
     *
     * IMPORTANT: `cachedIn` is applied BEFORE `combine` so the PagingData can be re-collected when
     * favourites change — applying it after combine collects the page flow twice and crashes.
     */
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
            MoviesListIntent.Load -> Unit // Paging auto-loads from the cache.
            MoviesListIntent.Refresh, MoviesListIntent.Retry -> refresh()
            is MoviesListIntent.SelectCategory -> selectedCategory.value = intent.category
            is MoviesListIntent.OpenDetails ->
                sendEffect(MoviesListEffect.NavigateToDetails(intent.movieId))
            is MoviesListIntent.ToggleFavorite -> toggleFavorite(intent.movieId)
            is MoviesListIntent.ConnectivityChanged,
            is MoviesListIntent.RefreshFinished,
            -> Unit // pure state transitions handled by the reducer
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            when (val result = refreshMovies(selectedCategory.value)) {
                is Outcome.Success -> sendIntent(MoviesListIntent.RefreshFinished(null))
                is Outcome.Failure -> {
                    // Soft failure: cache is still shown; surface a one-shot snackbar.
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
