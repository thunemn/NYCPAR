package com.example.nycpar.compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nycpar.R
import com.example.nycpar.api.TrailResponseItem
import com.example.nycpar.ui.theme.Accent
import com.example.nycpar.ui.theme.BackgroundLight
import com.example.nycpar.ui.theme.Black
import com.example.nycpar.ui.theme.White
import com.example.nycpar.utils.TextUtils
import com.example.nycpar.viewmodels.MainViewModel

@Composable
fun TrailsList(
    trails: List<TrailResponseItem>,
    navigateToDetails: (String) -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight),
        contentPadding = PaddingValues(
            horizontal = dimensionResource(id = R.dimen.trail_item_horiz_padding).value.dp,
            vertical = dimensionResource(id = R.dimen.trail_item_vertical_padding).value.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(trails) { trail ->
            trail.trailName?.let { trailName ->

                val isFavorite = if(trail.primaryKey != null) viewModel.isTrailFavorite(trail.primaryKey!!) else false

                Surface(
                    elevation = dimensionResource(id = R.dimen.trail_item_elevation).value.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(dimensionResource(id = R.dimen.trail_item_height).value.dp)
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.trail_item_padding).value.dp))
                        .background(White),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(dimensionResource(id = R.dimen.trail_item_padding))
                                .weight(9f)
                                .clickable {
                                    trail.primaryKey?.let { primaryKey ->
                                        viewModel.setDetailsItem(trail)
                                        navigateToDetails(primaryKey)
                                    }
                                },
                        ) {
                            //trail name (park name)
                            Text(
                                text = "$trailName (${trail.parkName})",
                                color = Black,
                                style = MaterialTheme.typography.body1,
                                textAlign = TextAlign.Start,
                                maxLines = 1
                            )
                            //surface/topography
                            Text(
                                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.trail_item_text_padding).value.dp),
                                text = TextUtils.getSurfaceTopogText(trail),
                                color = Black,
                                style = MaterialTheme.typography.body2,
                                textAlign = TextAlign.Start,
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            verticalArrangement = Arrangement.Center
                        ) {
                            //favorite icon
                            Icon(
                                imageVector = if(isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                tint = Accent,
                                contentDescription = stringResource(id = R.string.favorite),
                                modifier = Modifier
                                    .padding(horizontal = dimensionResource(id = R.dimen.trail_item_icon_padding).value.dp)
                                    .clickable(onClick = {
                                        if (isFavorite) {
                                            viewModel.removeTrailFromFavorites(trail)
                                        } else {
                                            //toggle favorite icon
                                            viewModel.addTrailToFavorites(trail)
                                        }
                                    })
                            )
                        }
                    }
                }
            }
        }
    }
}