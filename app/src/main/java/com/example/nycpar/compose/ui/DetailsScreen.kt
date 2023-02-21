package com.example.nycpar.compose.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nycpar.R
import com.example.nycpar.api.TrailResponseItem
import com.example.nycpar.models.Screens
import com.example.nycpar.ui.theme.*
import com.example.nycpar.utils.TextUtils
import com.example.nycpar.viewmodels.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    viewModel.updateCurrentScreen(Screens.DETAILS)

    val trailItem: TrailResponseItem? = viewModel.detailsItem.collectAsState().value

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        onDispose {
            viewModel.clearDetailsItem()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight),
        contentAlignment = Alignment.Center
    ) {
        trailItem?.let { trail ->
            Row(
                modifier = Modifier
                    .heightIn(min = 32.dp)
                    .widthIn(min = 32.dp)
                    .padding(dimensionResource(id = R.dimen.details_padding).value.dp)
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.details_corners).value.dp))
                    .background(White)
                    .padding(dimensionResource(id = R.dimen.details_padding).value.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(8.5f)
                ) {
                    //trail name (park name)
                    Text(
                        text = "${trail.trailName} (${trail.parkName})",
                        color = Black,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Start,
                    )
                    //surface/topography
                    Text(
                        modifier = Modifier.padding(vertical = dimensionResource(id = com.example.nycpar.R.dimen.trail_item_text_padding).value.dp),
                        text = TextUtils.getSurfaceTopogText(trail),
                        color = Black,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Start,
                    )
                    //difficulty
                    Text(
                        modifier = Modifier.padding(vertical = dimensionResource(id = com.example.nycpar.R.dimen.trail_item_text_padding).value.dp),
                        text = TextUtils.getDifficultyText(trail),
                        color = Black,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Start,
                    )
                    //width
                    Text(
                        modifier = Modifier.padding(vertical = dimensionResource(id = com.example.nycpar.R.dimen.trail_item_text_padding).value.dp),
                        text = TextUtils.getWidthText(trail),
                        color = Black,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Start,
                    )
                    //class
                    Text(
                        modifier = Modifier.padding(vertical = dimensionResource(id = com.example.nycpar.R.dimen.trail_item_text_padding).value.dp),
                        text = TextUtils.getClassText(trail),
                        color = Black,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Start,
                    )
                    //trail markers
                    Text(
                        modifier = Modifier.padding(vertical = dimensionResource(id = com.example.nycpar.R.dimen.trail_item_text_padding).value.dp),
                        text = TextUtils.getTrailMarkersText(trail),
                        color = Black,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Start,
                    )
                }

                Spacer(modifier = Modifier.weight(0.5f))

                Column(
                    modifier = Modifier
                        .weight(1.0f)
                ) {
                    //favorite icon
                    Icon(
                        imageVector = if(trailItem.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        tint = Accent,
                        contentDescription = stringResource(id = R.string.favorite),
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.details_fave_size).value.dp)
                            .clickable(onClick = {
                                if (trailItem.isFavorite) {
                                    viewModel.removeTrailFromFavorites(trail)
                                } else {
                                    viewModel.addTrailToFavorites(trail)
                                }
                            })
                    )
                }
            }
        } ?: run {
            //show some error
        }
    }
}