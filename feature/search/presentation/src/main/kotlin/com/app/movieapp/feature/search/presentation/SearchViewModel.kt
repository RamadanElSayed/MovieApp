package com.app.movieapp.feature.search.presentation

import androidx.lifecycle.viewModelScope
import com.app.movieapp.common.domain.result.Outcome
import com.app.movieapp.common.presentation.error.toMessage
import com.app.movieapp.common.presentation.mvi.BaseViewModel
import com.app.movieapp.common.presentation.resource.ResourceProvider
import com.app.movieapp.feature.search.domain.usecase.SearchMoviesUseCase
import com.app.movieapp.feature.search.presentation.model.toUi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val searchMovies: SearchMoviesUseCase,
    private val resources: ResourceProvider,
) : BaseViewModel<SearchState, SearchIntent, SearchEffect>(
    initialState = SearchState(),
    reducer = SearchReducer(),
) {
    private val queryFlow = MutableStateFlow("")

    init {
        queryFlow
            .debounce(DEBOUNCE_MS)
            .distinctUntilChanged()
            .onEach { runSearch(it) }
            .launchIn(viewModelScope)
    }

    override fun handleIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.QueryChanged -> queryFlow.value = intent.query
            SearchIntent.Retry -> runSearch(currentState.query)
            is SearchIntent.OpenDetails -> sendEffect(SearchEffect.NavigateToDetails(intent.movieId))
            is SearchIntent.ResultsLoaded, is SearchIntent.Failed, SearchIntent.Loading -> Unit
        }
    }

    private fun runSearch(query: String) {
        if (query.isBlank()) {
            sendIntent(SearchIntent.ResultsLoaded(emptyList()))
            return
        }
        viewModelScope.launch {
            sendIntent(SearchIntent.Loading)
            when (val result = searchMovies(query)) {
                is Outcome.Success ->
                    sendIntent(SearchIntent.ResultsLoaded(result.data.map { it.toUi() }))
                is Outcome.Failure ->
                    sendIntent(SearchIntent.Failed(result.error.toMessage(resources)))
            }
        }
    }

    private companion object {
        const val DEBOUNCE_MS = 350L
    }
}
