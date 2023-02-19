package com.example.nycpar.compose.ui

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.nycpar.R
import com.example.nycpar.api.ParkResponseItem
import com.example.nycpar.compose.ui.components.DrawerContent
import com.example.nycpar.compose.ui.components.NYCTopAppBar
import com.example.nycpar.compose.ui.components.StatusBar
import com.example.nycpar.models.Screens
import com.example.nycpar.ui.theme.*
import com.example.nycpar.viewmodels.MainViewModel
import com.example.nycpar.viewmodels.State
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlin.math.log

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TrailsScreen(
    modifier: Modifier = Modifier,
    navigateToDetails: (String) -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    viewModel.updateCurrentScreen(Screens.TRAILS)

    val trails: List<ParkResponseItem>? = viewModel.trails.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        Text(
            text = "${viewModel.trails.collectAsState().value?.size ?: "null"}",
            color = Color.Black,
            fontSize = dimensionResource(id = R.dimen.splash_name_textSize).value.sp,
            fontFamily = PatuaOneFontFamily,
        )

        //show loading indicator or error snackbar
        //success shows list of parks
        when(viewModel.state.collectAsState().value) {
            is State.Loading -> Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(size = dimensionResource(id = R.dimen.progress_spinner_size).value.dp),
                        color = Accent,
                        strokeWidth = dimensionResource(id = R.dimen.progress_spinner_width).value.dp
                    )
                }

            is State.Success -> trails?.let {
                showTrailsList(trails = it, navigateToDetails)
            }
            is State.Error -> {
                viewModel.showSnackBar()
            }
        }
    }
}

@Composable
fun showTrailsList(
    trails: List<ParkResponseItem>,
    navigateToDetails: (String) -> Unit,
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
        val validItems = trails.filter { it.trailName?.isNullOrEmpty() == false }

        items(validItems) { trail ->
            trail.trailName?.let { trailName ->
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
                                    navigateToDetails(trailName)
                                },
                        ) {
                            //trail name
                            Text(
                                text = "$trailName (${trail.parkName})",
                                color = Black,
                                style = MaterialTheme.typography.body1,
                                textAlign = TextAlign.Start,
                                maxLines = 1
                            )
                            //surface/difficulty
                            Text(
                                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.trail_item_text_padding).value.dp),
                                text = "Surface: ${trail.surface}, ${trail.topog}",
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
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = stringResource(id = R.string.favorite),
                                modifier = Modifier
                                    .padding(horizontal = dimensionResource(id = R.dimen.trail_item_icon_padding).value.dp)
                                    .clickable(onClick = {
                                        Toast.makeText(context, "$trailName favorite clicked", Toast.LENGTH_SHORT).show()
                                    })
                            )
                        }
                    }
                }
            }
        }
    }
}

//@Preview
@Composable
fun showList() {
    val parkJson = "{\"park_name\":\"Bronx Park\",\"width_ft\":\"2 feet to less than 4 feet\",\"class\":\"Class III : Developed/Improved\",\"surface\":\"Dirt\",\"gen_topog\":\"Level\",\"difficulty\":\"1: flat and smooth\",\"date_collected\":\"2023-02-14T00:00:00\",\"trail_name\":\"Blue Trail\",\"parkid\":\"X002\",\"trailmarkersinstalled\":\"No\"}"
    val park = Gson().fromJson(parkJson, ParkResponseItem::class.java)
    val parks = listOf(park, park)

//    showTrailsList(trails = parks, navigateToDetails = {
//        Log.d(TAG, "park clicked: $it")
//    })
}