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
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.nycpar.api.TrailResponseItem
import com.example.nycpar.compose.ui.components.DrawerContent
import com.example.nycpar.compose.ui.components.NYCTopAppBar
import com.example.nycpar.compose.ui.components.StatusBar
import com.example.nycpar.compose.ui.components.TrailsList
import com.example.nycpar.models.Screens
import com.example.nycpar.ui.theme.*
import com.example.nycpar.viewmodels.MainViewModel
import com.example.nycpar.viewmodels.State
import kotlinx.coroutines.launch
import kotlin.math.log

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    navigateToDetails: (String) -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    viewModel.updateCurrentScreen(Screens.FAVORITES)

    val trails: List<TrailResponseItem> = viewModel.trails.collectAsState().value

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        TrailsList(
            trails = trails,
            navigateToDetails,
            viewModel)
    }
}