package com.aliumujib.all.breeds.presentation.list

import androidx.compose.runtime.Immutable
import com.aliumujib.model.Breed
import com.aliumujib.model.BreedId


interface BreedsContract {

    @Immutable
    sealed class BreedsUiState(open val breeds: List<Breed>) {
        data class Success(override val breeds: List<Breed>) : BreedsUiState(emptyList())
        data object Loading : BreedsUiState(emptyList())
        data object Empty : BreedsUiState(emptyList())
        data class Error(val errorMessage: String?) : BreedsUiState(emptyList())
        data object Initial : BreedsUiState(emptyList())
    }

    sealed interface BreedsResult {
        sealed interface FetchCatBreedsResult : BreedsResult {
            data object Loading : FetchCatBreedsResult
            data class Error(val throwable: Throwable) : FetchCatBreedsResult
            data class Success(val data: List<Breed>) : FetchCatBreedsResult
        }

        sealed interface ToggleFavouriteStatusResult : BreedsResult {
            data class Error(val throwable: Throwable) : ToggleFavouriteStatusResult
            data class Success(val data: BreedId) : ToggleFavouriteStatusResult
        }
    }

    sealed interface BreedsUiIntent {
        data object FetchCatBreeds : BreedsUiIntent
        data class ToggleFavouriteStatus(val breedId: BreedId) : BreedsUiIntent
    }

    sealed interface BreedsSideEffect {
        data class ShowErrorToast(val error: String) : BreedsSideEffect
    }
}