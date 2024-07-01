package com.aliumujib.favorite.breeds.presentation

import androidx.compose.runtime.Immutable
import com.aliumujib.model.Breed

@Immutable
sealed class FavoritesUiState {
    data class Success(val breeds: List<Breed>) : FavoritesUiState()
    data object Loading : FavoritesUiState()
    data class Error(val error: String?) : FavoritesUiState()
    data object Initial : FavoritesUiState()
}
