package com.aliumujib.favorite.breeds.presentation


sealed interface FavoritesUiEvents {
    data class ShowError(val message: String) : FavoritesUiEvents
}