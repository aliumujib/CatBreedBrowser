package com.aliumujib.favorite.breeds.navigation

import com.aliumujib.model.BreedId

interface FavoritesNavigator {
    fun goToDetails(id: BreedId)
}
