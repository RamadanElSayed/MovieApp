package com.app.movieapp.feature.favorites.presentation

import androidx.lifecycle.viewModelScope
import com.app.movieapp.common.presentation.mvi.BaseViewModel
import com.app.movieapp.feature.favorites.domain.usecase.ObserveFavoriteMoviesUseCase
import com.app.movieapp.feature.favorites.domain.usecase.ToggleFavoriteUseCase
import com.app.movieapp.feature.favorites.presentation.model.toUi
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val observeFavorites: ObserveFavoriteMoviesUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase,
) : BaseViewModel<FavoritesState, FavoritesIntent, FavoritesEffect>(
    initialState = FavoritesState(),
    reducer = FavoritesReducer(),
) {
    init {
        observe()
    }

    override fun handleIntent(intent: FavoritesIntent) {
        when (intent) {
            FavoritesIntent.Load -> Unit
            is FavoritesIntent.Remove ->
                viewModelScope.launch { toggleFavorite(intent.movieId) }
            is FavoritesIntent.OpenDetails ->
                sendEffect(FavoritesEffect.NavigateToDetails(intent.movieId))
            is FavoritesIntent.ItemsLoaded -> Unit
        }
    }

    private fun observe() {
        viewModelScope.launch {
            observeFavorites(Unit).collect { summaries ->
                sendIntent(FavoritesIntent.ItemsLoaded(summaries.map { it.toUi() }))
            }
        }
    }
}
