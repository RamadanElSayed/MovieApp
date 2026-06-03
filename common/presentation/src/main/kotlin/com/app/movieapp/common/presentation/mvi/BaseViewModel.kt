package com.app.movieapp.common.presentation.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * MVI base ViewModel implementing unidirectional data flow:
 *
 *   UI --(Intent)--> [onIntent] --(Reducer)--> new [state]   (rendered)
 *                                  \--(optional)--> [effect]  (one-shot)
 *
 * - [state] is the single immutable source of truth, exposed as a [StateFlow].
 * - [effect] is a [Channel]-backed [Flow] for one-shot events (never replayed on rotation).
 * - State is only ever mutated through the pure [reducer] via [sendIntent].
 */
abstract class BaseViewModel<S : UiState, I : Intent, E : Effect>(
    initialState: S,
    private val reducer: Reducer<S, I>,
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val _effect = Channel<E>(Channel.BUFFERED)
    val effect: Flow<E> = _effect.receiveAsFlow()

    val currentState: S get() = _state.value

    /** Entry point for the UI. Reduces synchronously, then runs any side-effecting handler. */
    fun sendIntent(intent: I) {
        _state.update { reducer.reduce(it, intent) }
        handleIntent(intent)
    }

    /** Override to launch async work (load/refresh) in response to an intent. */
    protected open fun handleIntent(intent: I) = Unit

    /** Imperatively update state from async results (e.g. after a repository call returns). */
    protected fun setState(transform: (S) -> S) = _state.update(transform)

    protected fun sendEffect(effect: E) {
        viewModelScope.launch { _effect.send(effect) }
    }
}
