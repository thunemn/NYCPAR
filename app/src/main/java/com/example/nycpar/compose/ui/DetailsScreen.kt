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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nycpar.R
import com.example.nycpar.api.TrailResponseItem
import com.example.nycpar.models.Screens
import com.example.nycpar.ui.theme.*
import com.example.nycpar.utils.Utils
import com.example.nycpar.viewmodels.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    primaryKey: String,
    viewModel: MainViewModel = viewModel()
) {
    viewModel.updateCurrentScreen(Screens.DETAILS)

    val trailItem: TrailResponseItem? = viewModel.getTrailDetails(primaryKey)
    Log.d(TAG, "trail details: $trailItem")

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
                Column {
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
                        text = Utils.getSurfaceTopogText(trail),
                        color = Black,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Start,
                    )
                    //difficulty
                    Text(
                        modifier = Modifier.padding(vertical = dimensionResource(id = com.example.nycpar.R.dimen.trail_item_text_padding).value.dp),
                        text = "asdjfklasjdf ajklsdf jakf ajlkdsf jakf ajsdf aksjf ajd fjas fkljaskd fjklds fjaskldf",
                        color = Black,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Start,
                    )
                    //width
                    Text(
                        modifier = Modifier.padding(vertical = dimensionResource(id = com.example.nycpar.R.dimen.trail_item_text_padding).value.dp),
                        text = Utils.getWidthText(trail),
                        color = Black,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Start,
                    )
                    //class
                    Text(
                        modifier = Modifier.padding(vertical = dimensionResource(id = com.example.nycpar.R.dimen.trail_item_text_padding).value.dp),
                        text = Utils.getClassText(trail),
                        color = Black,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Start,
                    )
                    //trail markers
                    Text(
                        modifier = Modifier.padding(vertical = dimensionResource(id = com.example.nycpar.R.dimen.trail_item_text_padding).value.dp),
                        text = Utils.getTrailMarkersText(trail),
                        color = Black,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Start,
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                ) {
                    //favorite icon
                    Icon(
                        imageVector = if(trail.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        tint = Accent,
                        contentDescription = stringResource(id = R.string.favorite),
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(id = R.dimen.trail_item_icon_padding).value.dp)
                            .clickable(onClick = {
                                if (trail.isFavorite) {
                                    viewModel.removeTrailFromFavorites(trail)
                                } else {
                                    //toggle favorite icon
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