package com.aliumujib.all.breeds.ui

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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aliumujib.all.breeds.R
import com.aliumujib.all.breeds.navigation.BreedsNavigator
import com.aliumujib.all.breeds.presentation.list.BreedsContract
import com.aliumujib.all.breeds.presentation.list.BreedsViewModel
import com.aliumujib.model.Breed
import com.aliumujib.breed.common.ui.BreedDetailsSummaryBottomSheet
import com.aliumujib.breed.common.ui.BreedListItem
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun BreedsScreen(
    navigator: BreedsNavigator,
    viewModel: BreedsViewModel = hiltViewModel()
) {
    var isMoreSheetOpen by remember { mutableStateOf(false) }
    var focusedBreed by remember { mutableStateOf<Breed?>(null) }

    val uiState by viewModel.states.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.start()
    }

    BreedsScreenContent(
        isMoreSheetOpen = isMoreSheetOpen,
        uiState = uiState,
        focusedBreed = focusedBreed,
        onItemClicked = { breed ->
            navigator.goToDetails(breed.id)
        },
        onMoreClick = {
            focusedBreed = it
            isMoreSheetOpen = true
        },
        onMoreDismissedRequest = {
            focusedBreed = null
            isMoreSheetOpen = false
        },
        onFavoriteClick = {
            focusedBreed = it.copy(isFavorite = !it.isFavorite)
            viewModel.onAction(BreedsContract.BreedsUiIntent.ToggleFavouriteStatus(it.id))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedsScreenContent(
    isMoreSheetOpen: Boolean,
    uiState: BreedsContract.BreedsUiState,
    focusedBreed: Breed?,
    onItemClicked: (Breed) -> Unit,
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
        val breeds = uiState.breeds

        Scaffold(
            topBar = {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(id = R.string.cats_tab_title),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(id = R.string.cats_tab_sub_title),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
        ) { values ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values),
                state = lazyListState,
            ) {

                when (uiState) {
                    is BreedsContract.BreedsUiState.Error -> {
                        item {
                            Text(
                                text = uiState.errorMessage ?: stringResource(id = R.string.default_error_message),
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    BreedsContract.BreedsUiState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillParentMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator(Modifier.testTag("Loading"))
                            }
                        }
                    }

                    is BreedsContract.BreedsUiState.Success -> {
                        itemsIndexed(breeds,
                            key = { _, breed -> breed.id }) { _, item ->
                            BreedListItem(
                                breed = item,
                                onItemClick = onItemClicked,
                                onMoreClick = {
                                    onMoreClick(it)
                                }
                            )
                        }
                    }

                    BreedsContract.BreedsUiState.Initial -> {

                    }

                    BreedsContract.BreedsUiState.Empty -> {
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