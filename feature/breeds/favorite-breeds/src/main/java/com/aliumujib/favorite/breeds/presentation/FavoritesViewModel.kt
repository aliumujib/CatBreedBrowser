package com.aliumujib.favorite.breeds.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliumujib.model.Breed
import com.aliumujib.songs.domain.usecases.StreamFavoritesListUseCase
import com.aliumujib.songs.domain.usecases.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    streamFavoritesListUseCase: StreamFavoritesListUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    val states: StateFlow<FavoritesUiState> =
        streamFavoritesListUseCase()
            .distinctUntilChanged()
            .map<List<Breed>, FavoritesUiState> { data -> FavoritesUiState.Success(data) }
            .catch { emit(FavoritesUiState.Error(it.message)) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = FavoritesUiState.Initial,
            )

    private val _events = MutableSharedFlow<FavoritesUiEvents>()
    val events: SharedFlow<FavoritesUiEvents> = _events.asSharedFlow()

    fun toggleFavorite(breed: Breed) {
        viewModelScope.launch {
            toggleFavoriteUseCase(breed.id)
                .onFailure {
                    _events.emit(FavoritesUiEvents.ShowError(it.message ?: "Unknown error"))
                }
        }
    }

}