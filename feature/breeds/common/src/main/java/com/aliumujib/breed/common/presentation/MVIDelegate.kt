package com.aliumujib.breed.common.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MVIDelegate<State, Intent, Result, SideEffect> internal constructor(
    private val initialUiState: State,
    private val intentProcessor: IntentProcessor<Intent, Result>,
    private val stateReducer: StateReducer<State, Result>,
) : ViewModel(), MVI<State, Intent, Result, SideEffect> {

    private val _states = MutableStateFlow(initialUiState)
    override val states = _states.asStateFlow()

    private val _intents = MutableSharedFlow<Intent>(1)
    override val intents = _intents.asSharedFlow()

    private val _sideEffect by lazy { Channel<SideEffect>() }
    override val sideEffect: Flow<SideEffect> by lazy { _sideEffect.receiveAsFlow() }

    override fun processActions() {
        _intents.flatMapMerge {
            intentProcessor.intentToResult(it)
        }
            .scan(initial = initialUiState) { old: State, result: Result ->
                stateReducer.reduce(old, result)
            }
            .distinctUntilChanged()
            .onEach {
                _states.emit(it)
            }.launchIn(viewModelScope)
    }

    override fun onAction(action: Intent) {
        viewModelScope.launch {
            _intents.emit(action)
        }
    }

    override fun emitSideEffect(effect: SideEffect) {
        viewModelScope.launch { _sideEffect.send(effect) }
    }
}

fun <State, Intent, Result, SideEffect> mvi(
    initialUiState: State,
    intentProcessor: IntentProcessor<Intent, Result>,
    stateReducer: StateReducer<State, Result>,
): MVI<State, Intent, Result, SideEffect> =
    MVIDelegate(
        initialUiState, intentProcessor, stateReducer
    )