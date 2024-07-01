package com.aliumujib.breed.details.presentation

import com.aliumujib.breed.common.presentation.StateReducer
import javax.inject.Inject

class BreedDetailsStateReducer @Inject constructor() :
    StateReducer<BreedDetailsContract.BreedDetailsUiState, BreedDetailsContract.BreedDetailsResult> {

    override fun reduce(
        oldState: BreedDetailsContract.BreedDetailsUiState,
        result: BreedDetailsContract.BreedDetailsResult
    ): BreedDetailsContract.BreedDetailsUiState {
        return when (result) {
            is BreedDetailsContract.BreedDetailsResult.FetchCatBreedDetailsResult -> {
                when (result) {
                    is BreedDetailsContract.BreedDetailsResult.FetchCatBreedDetailsResult.Error -> {
                        BreedDetailsContract.BreedDetailsUiState.Error(result.throwable.message)
                    }

                    BreedDetailsContract.BreedDetailsResult.FetchCatBreedDetailsResult.Loading -> {
                        BreedDetailsContract.BreedDetailsUiState.Loading
                    }

                    is BreedDetailsContract.BreedDetailsResult.FetchCatBreedDetailsResult.Success -> {
                        BreedDetailsContract.BreedDetailsUiState.Success(result.data)
                    }
                }
            }

            is BreedDetailsContract.BreedDetailsResult.ToggleFavouriteStatusResult -> {
                when (result) {
                    is BreedDetailsContract.BreedDetailsResult.ToggleFavouriteStatusResult.Error -> {
                        BreedDetailsContract.BreedDetailsUiState.Error(result.throwable.message)
                    }

                    is BreedDetailsContract.BreedDetailsResult.ToggleFavouriteStatusResult.Success -> {
                        BreedDetailsContract.BreedDetailsUiState.Initial
                    }
                }
            }
        }
    }

}
