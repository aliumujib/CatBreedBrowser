package com.aliumujib.catbrowser.navigation

import androidx.navigation.NavController
import com.aliumujib.all.breeds.navigation.BreedsNavigator
import com.aliumujib.favorite.breeds.navigation.FavoritesNavigator
import com.aliumujib.model.BreedId
import com.aliumujib.breed.details.navigator.BreedDetailsNavigator
import com.aliumujib.breed.details.ui.destinations.CatBreedDetailsScreenDestination
import com.ramcosta.composedestinations.dynamic.within
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.NavGraphSpec

class CoreFeatureNavigator(
    private val navGraph: NavGraphSpec,
    private val navController: NavController,
) : FavoritesNavigator, BreedsNavigator, BreedDetailsNavigator {

    override fun goToDetails(id: BreedId) {
        navController.navigate(CatBreedDetailsScreenDestination(breedId = id) within navGraph)
    }

    override fun goToBack() {
        navController.navigate(navController.graph.startDestinationId)
    }

}
