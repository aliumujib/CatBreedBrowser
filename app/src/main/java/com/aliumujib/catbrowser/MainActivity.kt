package com.aliumujib.catbrowser

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aliumujib.all.breeds.ui.destinations.BreedsScreenDestination
import com.aliumujib.designsystem.theme.AppTheme
import com.aliumujib.designsystem.theme.Theme
import com.aliumujib.catbrowser.component.StandardScaffold
import com.aliumujib.catbrowser.component.navGraph
import com.aliumujib.catbrowser.navigation.CoreFeatureNavigator
import com.aliumujib.catbrowser.navigation.NavGraphs
import com.aliumujib.favorite.breeds.ui.destinations.FavoritesScreenDestination
import com.aliumujib.settings.presentation.settings.destinations.SettingsScreenDestination
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.DependenciesContainerBuilder
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private var navigator: CoreFeatureNavigator? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            viewModel.setGrantedPermissions(isGranted)
        }

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition(
                condition = viewModel
            )
        }

        requestPermission()

        setContent {
            val state by viewModel.state.collectAsState()

            val themeValue by viewModel.theme.collectAsState(
                initial = Theme.FOLLOW_SYSTEM.themeValue,
                context = Dispatchers.Main.immediate
            )

            AppTheme(
                theme = themeValue
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val newBackStackEntry by navController.currentBackStackEntryAsState()
                    val route = newBackStackEntry?.destination?.route

                    if (state.hasGrantedPermissions) {

                        StandardScaffold(
                            navController = navController,
                            items = getNavItems(context = this),
                            isLoggedIn = true,
                            showBottomBar = route in listOf(
                                "home/${BreedsScreenDestination.route}",
                                "favorites/${FavoritesScreenDestination.route}",
                                "settings/${SettingsScreenDestination.route}",
                            )
                        ) { innerPadding ->
                            Column(
                                modifier = Modifier.padding(innerPadding),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                AppNavigation(
                                    navController = navController,
                                    modifier = Modifier.weight(1f),
                                )
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            CircularProgressIndicator()

                            Text(
                                text = stringResource(id = R.string.home_screen_grant_storage_permissions),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialNavigationApi::class)
    @ExperimentalAnimationApi
    @Composable
    internal fun AppNavigation(
        navController: NavHostController,
        modifier: Modifier = Modifier,
    ) {
        val navHostEngine = rememberAnimatedNavHostEngine(
            navHostContentAlignment = Alignment.TopCenter,
            rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING,
            defaultAnimationsForNestedNavGraph = mapOf(
                NavGraphs.home to NestedNavGraphDefaultAnimations(),
                NavGraphs.favorites to NestedNavGraphDefaultAnimations(),
                NavGraphs.settings to NestedNavGraphDefaultAnimations(),
            )
        )

        DestinationsNavHost(
            engine = navHostEngine,
            navController = navController,
            navGraph = NavGraphs.root(),
            modifier = modifier,
            dependenciesContainerBuilder = {
                dependency(
                    currentNavigator()
                )
            }
        )
    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_AUDIO
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }
        )
    }

    private fun DependenciesContainerBuilder<*>.currentNavigator(): CoreFeatureNavigator {
        return CoreFeatureNavigator(
            navGraph = navBackStackEntry.destination.navGraph(),
            navController = navController,
        ).also { navigator = it }
    }

}
