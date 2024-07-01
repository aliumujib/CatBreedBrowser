package com.aliumujib.breed.common.presentation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MVI<State, Intent, Result, SideEffect> {
    val states: StateFlow<State>
    val intents: Flow<Intent>
    val sideEffect: Flow<SideEffect>

    fun onAction(action: Intent)

    fun emitSideEffect(effect: SideEffect)

    fun processActions()
}