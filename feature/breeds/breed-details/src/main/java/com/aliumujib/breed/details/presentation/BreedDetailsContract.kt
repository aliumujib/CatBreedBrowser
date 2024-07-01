package com.aliumujib.breed.details.presentation

import androidx.compose.runtime.Immutable
import com.aliumujib.model.Breed
import com.aliumujib.model.BreedId

interface BreedDetailsContract {

    @Immutable
    sealed class BreedDetailsUiState {
        data class Success(val breed: Breed) : BreedDetailsUiState()
        data object Loading : BreedDetailsUiState()
        data class Error(val errorMessage: String?) : BreedDetailsUiState()
        data object Initial : BreedDetailsUiState()
    }

    sealed interface BreedDetailsResult {
        sealed interface FetchCatBreedDetailsResult : BreedDetailsResult {
            data object Loading : FetchCatBreedDetailsResult
            data class Error(val throwable: Throwable) : FetchCatBreedDetailsResult
            data class Success(val data: Breed) : FetchCatBreedDetailsResult
        }

        sealed interface ToggleFavouriteStatusResult : BreedDetailsResult {
            data class Error(val throwable: Throwable) : ToggleFavouriteStatusResult
            data class Success(val data: BreedId) : ToggleFavouriteStatusResult
        }
    }

    sealed interface BreedDetailsUiIntent {
        data class FetchCatBreedDetails(val breedId: BreedId) : BreedDetailsUiIntent
        data class ToggleFavouriteStatus(val breedId: BreedId) : BreedDetailsUiIntent
    }

    sealed interface BreedDetailsSideEffect {
        data class ShowErrorToast(val error: String) : BreedDetailsSideEffect
    }
}