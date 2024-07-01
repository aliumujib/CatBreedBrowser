package com.aliumujib.breed.common.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aliumujib.model.Breed
import com.aliumujib.songs.commons.R
import io.eyram.iconsax.IconSax

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BreedDetailsSummaryBottomSheet(
    breed: Breed,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onFavoriteClick: (Breed) -> Unit,
) {
    ModalBottomSheet(
        modifier = Modifier.wrapContentHeight(),
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        BreedDetailsSummaryBottomSheetContent(
            breed = breed,
            onFavoriteClick = onFavoriteClick
        )
    }
}

@Composable
private fun BreedDetailsSummaryBottomSheetContent(
    breed: Breed,
    onFavoriteClick: (Breed) -> Unit,
) {
    val context = LocalContext.current
    val description = breed.attributes.description
    val weight = breed.weight.metric
    val temperament = breed.attributes.temperament

    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxWidth(), horizontalAlignment = Alignment.Start
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp,
                bottomStart = 4.dp,
                bottomEnd = 4.dp
            ),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(breed.referenceImageUrl)
                        .placeholder(R.drawable.cat_default)
                        .error(R.drawable.cat_default)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(15.dp)
                        .clip(RoundedCornerShape(15)),
                    contentScale = ContentScale.Crop
                )

                Column(Modifier.weight(1f)) {
                    Text(
                        text = breed.name,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = breed.attributes.origin,
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    modifier = Modifier,
                    onClick = {
                        onFavoriteClick(breed)
                    }) {
                    Icon(
                        painter = painterResource(
                            id = if (breed.isFavorite) {
                                IconSax.Bold.Heart
                            } else {
                                IconSax.Outline.Heart
                            }
                        ),
                        contentDescription = null
                    )
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp),
            shape = RoundedCornerShape(
                topStart = 4.dp,
                topEnd = 4.dp,
                bottomStart = 24.dp,
                bottomEnd = 24.dp
            ),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer),
        ) {
            Column(modifier = Modifier.padding(15.dp)) {
                Text(
                    text = "${stringResource(id = R.string.description)}: $description",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    text = "${stringResource(id = R.string.weight)}: $weight",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    text = "${stringResource(id = R.string.temperament)}: $temperament",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }
        }
    }
}