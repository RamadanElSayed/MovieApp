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

abstract class BaseViewModel<S : UiState, I : Intent, E : Effect>(
    initialState: S,
    private val reducer: Reducer<S, I>,
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val _effect = Channel<E>(Channel.BUFFERED)
    val effect: Flow<E> = _effect.receiveAsFlow()

    val currentState: S get() = _state.value

    fun sendIntent(intent: I) {
        _state.update { reducer.reduce(it, intent) }
        handleIntent(intent)
    }

    protected open fun handleIntent(intent: I) = Unit

    protected fun setState(transform: (S) -> S) = _state.update(transform)

    protected fun sendEffect(effect: E) {
        viewModelScope.launch { _effect.send(effect) }
    }
}
