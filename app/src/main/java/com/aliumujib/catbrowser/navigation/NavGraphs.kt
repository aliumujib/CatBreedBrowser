package com.aliumujib.catbrowser.navigation

import com.aliumujib.all.breeds.ui.destinations.BreedsScreenDestination
import com.aliumujib.favorite.breeds.ui.destinations.FavoritesScreenDestination
import com.aliumujib.breed.details.ui.destinations.CatBreedDetailsScreenDestination
import com.aliumujib.settings.presentation.settings.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec

object NavGraphs {

    val home = object : NavGraphSpec {
        override val route = "home"

        override val startRoute = BreedsScreenDestination routedIn this

        override val destinationsByRoute = listOf<DestinationSpec<*>>(
            BreedsScreenDestination, CatBreedDetailsScreenDestination
        ).routedIn(this)
            .associateBy { it.route }
    }

    val favorites = object : NavGraphSpec {
        override val route = "favorites"

        override val startRoute = FavoritesScreenDestination routedIn this

        override val destinationsByRoute = listOf<DestinationSpec<*>>(
            FavoritesScreenDestination, CatBreedDetailsScreenDestination
        ).routedIn(this)
            .associateBy { it.route }
    }

    val settings = object : NavGraphSpec {
        override val route = "settings"

        override val startRoute = SettingsScreenDestination routedIn this

        override val destinationsByRoute = listOf<DestinationSpec<*>>(
            SettingsScreenDestination,
        ).routedIn(this)
            .associateBy { it.route }
    }

    fun root() = object : NavGraphSpec {
        override val route = "root"
        override val startRoute = home
        override val destinationsByRoute = emptyMap<String, DestinationSpec<*>>()
        override val nestedNavGraphs = listOf(
            home,
            settings,
            favorites
        )
    }
}
