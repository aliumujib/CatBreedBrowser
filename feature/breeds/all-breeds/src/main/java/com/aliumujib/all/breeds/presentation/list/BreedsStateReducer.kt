package com.aliumujib.all.breeds.presentation.list

import com.aliumujib.breed.common.presentation.StateReducer
import javax.inject.Inject

class BreedsStateReducer @Inject constructor() :
    StateReducer<BreedsContract.BreedsUiState, BreedsContract.BreedsResult> {

    override fun reduce(
        oldState: BreedsContract.BreedsUiState,
        result: BreedsContract.BreedsResult
    ): BreedsContract.BreedsUiState {
        return when (result) {
            is BreedsContract.BreedsResult.FetchCatBreedsResult -> {
                when (result) {
                    is BreedsContract.BreedsResult.FetchCatBreedsResult.Error -> {
                        BreedsContract.BreedsUiState.Error(result.throwable.message)
                    }

                    BreedsContract.BreedsResult.FetchCatBreedsResult.Loading -> {
                        BreedsContract.BreedsUiState.Loading
                    }

                    is BreedsContract.BreedsResult.FetchCatBreedsResult.Success -> {
                        if (result.data.isEmpty()) {
                            BreedsContract.BreedsUiState.Empty
                        } else {
                            BreedsContract.BreedsUiState.Success(result.data)
                        }
                    }
                }
            }

            is BreedsContract.BreedsResult.ToggleFavouriteStatusResult -> {
                when (result) {
                    is BreedsContract.BreedsResult.ToggleFavouriteStatusResult.Error -> {
                        BreedsContract.BreedsUiState.Error(result.throwable.message)
                    }

                    is BreedsContract.BreedsResult.ToggleFavouriteStatusResult.Success -> {
                        val updated = oldState.breeds.map {
                            if (it.id == result.data) it.copy(isFavorite = !it.isFavorite) else it
                        }
                        BreedsContract.BreedsUiState.Success(updated)
                    }
                }
            }
        }
    }

}
