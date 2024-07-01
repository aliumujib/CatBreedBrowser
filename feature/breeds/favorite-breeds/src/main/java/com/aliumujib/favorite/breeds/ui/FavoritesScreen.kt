package com.aliumujib.favorite.breeds.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aliumujib.breed.common.ui.BreedDetailsSummaryBottomSheet
import com.aliumujib.breed.common.ui.BreedListItem
import com.aliumujib.favorite.breeds.navigation.FavoritesNavigator
import com.aliumujib.favorite.breeds.presentation.FavoritesUiEvents
import com.aliumujib.favorite.breeds.presentation.FavoritesUiState
import com.aliumujib.favorite.breeds.presentation.FavoritesViewModel
import com.aliumujib.favorite.songs.R
import com.aliumujib.model.Breed
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun FavoritesScreen(
    navigator: FavoritesNavigator,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    var isMoreSheetOpen by remember { mutableStateOf(false) }
    var focusedBreed by remember { mutableStateOf<Breed?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    val uiState by viewModel.states.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is FavoritesUiEvents.ShowError -> {
                    snackbarHostState.showSnackbar(message = event.message)
                }
            }
        }
    }

    FavoritesScreenContent(
        isMoreSheetOpen = isMoreSheetOpen,
        uiState = uiState,
        focusedBreed = focusedBreed,
        onSongPlaybackRequested = { breed ->
            navigator.goToDetails(breed.id)
        }, onMoreClick = {
            focusedBreed = it
            isMoreSheetOpen = true
        }, onMoreDismissedRequest = {
            focusedBreed = null
            isMoreSheetOpen = false
        }, onFavoriteClick = {
            focusedBreed = it.copy(isFavorite = !it.isFavorite)
            viewModel.toggleFavorite(breed = it)
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreenContent(
    isMoreSheetOpen: Boolean,
    uiState: FavoritesUiState,
    focusedBreed: Breed?,
    onSongPlaybackRequested: (Breed) -> Unit,
    onMoreClick: (Breed) -> Unit,
    onMoreDismissedRequest: () -> Unit,
    onFavoriteClick: (Breed) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val sheetState = rememberModalBottomSheetState()
        val lazyListState = rememberLazyListState()
        //val songs = uiState.breeds

        Scaffold(
            topBar = {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(id = R.string.favorites_tab_title),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(id = R.string.favorites_tab_sub_title),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
        ) { values ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values),
                state = lazyListState
            ) {
                when (uiState) {
                    is FavoritesUiState.Error -> {
                        item {
                            Text(
                                text = "No music found !",
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    FavoritesUiState.Initial -> {

                    }

                    FavoritesUiState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    is FavoritesUiState.Success -> {
                        if (uiState.breeds.isEmpty()) {
                            item {
                                Text(
                                    text = "No favorites found !",
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxSize(),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        } else {
                            itemsIndexed(uiState.breeds,
                                key = { _, song -> song.id }) { _, item ->
                                BreedListItem(
                                    breed = item,
                                    onItemClick = { onSongPlaybackRequested(it) },
                                    onMoreClick = {
                                        onMoreClick(it)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        if (isMoreSheetOpen && focusedBreed != null) {
            BreedDetailsSummaryBottomSheet(
                breed = focusedBreed,
                sheetState = sheetState,
                onDismissRequest = onMoreDismissedRequest,
                onFavoriteClick = onFavoriteClick
            )
        }
    }
}