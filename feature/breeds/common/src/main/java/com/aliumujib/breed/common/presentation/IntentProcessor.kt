package com.aliumujib.breed.common.presentation

import kotlinx.coroutines.flow.Flow

interface IntentProcessor<Intent, Result> {
    fun intentToResult(viewIntent: Intent): Flow<Result>
}

class InvalidViewIntentException(
    private val intent: Any
) : IllegalArgumentException() {
    override val message: String
        get() = "Invalid intent $intent"
}