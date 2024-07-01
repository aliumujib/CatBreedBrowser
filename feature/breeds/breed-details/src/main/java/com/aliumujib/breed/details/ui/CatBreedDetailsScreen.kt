package com.aliumujib.breed.details.ui

import androidx.annotation.OptIn
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aliumujib.model.BreedId
import com.aliumujib.breed.details.navigator.BreedDetailsNavigator
import com.aliumujib.breed.details.presentation.BreedDetailsContract
import com.aliumujib.breed.details.presentation.BreedDetailsViewModel
import com.aliumujib.songs.commons.R
import com.ramcosta.composedestinations.annotation.Destination
import dagger.hilt.android.UnstableApi
import io.eyram.iconsax.IconSax

@OptIn(UnstableApi::class)
@Composable
@Destination
fun CatBreedDetailsScreen(
    breedId: BreedId,
    viewModel: BreedDetailsViewModel = hiltViewModel(),
    navigator: BreedDetailsNavigator,
) {
    val uiState by viewModel.states.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.start(breedId)
    }

    CatBreedDetailsContent(
        state = uiState,
        onNavigateUp = navigator::goToBack,
    )
}

@Composable
fun CatBreedDetailsContent(
    state: BreedDetailsContract.BreedDetailsUiState,
    onNavigateUp: () -> Unit,
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Row(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.clickable(onClick = onNavigateUp),
                    painter = painterResource(id = IconSax.Outline.ArrowLeft),
                    contentDescription = "Close"
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(
                    top = padding.calculateTopPadding() + 15.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                when (state) {
                    is BreedDetailsContract.BreedDetailsUiState.Error -> {
                        item {
                            Text(
                                text = state.errorMessage ?: "An error occurred!",
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    BreedDetailsContract.BreedDetailsUiState.Initial -> {

                    }

                    BreedDetailsContract.BreedDetailsUiState.Loading -> {
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

                    is BreedDetailsContract.BreedDetailsUiState.Success -> {
                        item {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .placeholder(R.drawable.cat_default)
                                    .error(R.drawable.cat_default)
                                    .data(state.breed.referenceImageUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(340.dp)
                                    .clip(RoundedCornerShape(15)),
                                contentScale = ContentScale.Crop
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = state.breed.name,
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = state.breed.name,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            BreedDetailRowItem(
                                icon = painterResource(id = IconSax.Linear.Clipboard),
                                title = stringResource(id = R.string.description),
                                content = state.breed.attributes.description,
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            BreedAttributeRowItem(
                                icon = painterResource(id = IconSax.Linear.Speedometer),
                                content = stringResource(
                                    id = R.string.temperament,
                                    state.breed.attributes.temperament
                                ),
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            BreedAttributeRowItem(
                                icon = painterResource(id = IconSax.Linear.Flag),
                                content = stringResource(
                                    id = R.string.origin,
                                    state.breed.attributes.origin
                                ),
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            BreedAttributeRowItem(
                                icon = painterResource(id = IconSax.Linear.Cake),
                                content = stringResource(
                                    id = R.string.life_span,
                                    state.breed.attributes.lifeSpan
                                ),
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            BreedAttributeRowItem(
                                icon = painterResource(id = IconSax.Linear.Courthouse),
                                content = stringResource(
                                    id = R.string.lap,
                                    state.breed.attributes.lap
                                ),
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            BreedAttributeRowItem(
                                icon = painterResource(id = IconSax.Linear.Tag),
                                content = stringResource(
                                    id = R.string.alt_names,
                                    state.breed.attributes.altNames
                                ),
                            )
                        }
                    }
                }

            }
        }

    }
}

@Composable
private fun BreedDetailRowItem(
    icon: Painter,
    title: String,
    content: String,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = icon, contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun BreedAttributeRowItem(
    icon: Painter,
    content: String,
    modifier: Modifier = Modifier,
) {

    Row(modifier = modifier, verticalAlignment = Alignment.Top) {
        Icon(painter = icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis
        )
    }
}







