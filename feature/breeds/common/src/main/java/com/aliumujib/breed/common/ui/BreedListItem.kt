package com.aliumujib.breed.common.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.aliumujib.model.Breed
import com.aliumujib.songs.commons.R
import io.eyram.iconsax.IconSax

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnsafeOptInUsageError")
@Composable
fun BreedListItem(
    modifier: Modifier = Modifier,
    breed: Breed,
    onItemClick: (Breed) -> Unit,
    onMoreClick: (Breed) -> Unit,
) {

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .combinedClickable(
                onClick = { onItemClick(breed) },
            )
    ) {

        Column(modifier = Modifier) {

            val imageLoader = LocalContext.current.imageLoader

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(breed.referenceImageUrl)
                    .placeholder(R.drawable.cat_default)
                    .error(R.drawable.cat_default)
                    .crossfade(true)
                    .build(),
                contentDescription = "Artwork",
                imageLoader = imageLoader,
                modifier = Modifier
                    .height(150.dp)
                    .clip(RoundedCornerShape(topEnd = 5.dp, topStart = 5.dp)),
                contentScale = ContentScale.Crop,
            )

            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = breed.name,
                        style = MaterialTheme.typography.bodyLarge,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        text = breed.attributes.description,
                        style = MaterialTheme.typography.bodySmall,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3
                    )
                }

                IconButton(
                    modifier = Modifier.graphicsLayer(rotationZ = 90f),
                    onClick = {
                        onMoreClick(breed)
                    }) {
                    Icon(
                        painter = painterResource(
                            id = IconSax.Linear.More
                        ),
                        contentDescription = null
                    )
                }
            }
        }
    }
}
