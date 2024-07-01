package com.aliumujib.breed.common.presentation

public interface StateReducer<State, Result> {
    public fun reduce(oldState: State, result: Result): State
}
