package ru.aiadvent.mobile.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class BaseViewModel<State : Any, Event : Any, Effect : Any>(
    initialState: State
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)

    protected val scope = viewModelScope + CoroutineExceptionHandler { _, error ->
        Log.e("BaseViewModel", "CoroutineExceptionHandler", error)
    }

    val state: StateFlow<State> = _state.asStateFlow()

    val current: State get() = state.value

    private val _effects: Channel<Effect> = Channel(capacity = Channel.BUFFERED)

    val effects: Flow<Effect> = _effects.receiveAsFlow()

    abstract fun handleEvent(event: Event)

    fun onEvent(event: Event) = handleEvent(event)

    protected fun launch(block: suspend () -> Unit) = scope.launch { block() }

    protected fun setState(reducer: State.() -> State) {
        _state.update(reducer)
    }

    protected fun setEffect(builder: () -> Effect) {
        scope.launch { _effects.send(builder()) }
    }
}
