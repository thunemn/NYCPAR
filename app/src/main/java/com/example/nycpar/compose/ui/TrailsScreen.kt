package com.example.nycpar.compose.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nycpar.R
import com.example.nycpar.api.TrailResponseItem
import com.example.nycpar.compose.ui.components.TrailsList
import com.example.nycpar.models.Screens
import com.example.nycpar.ui.theme.*
import com.example.nycpar.viewmodels.MainViewModel
import com.example.nycpar.viewmodels.State
import com.google.gson.Gson

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TrailsScreen(
    modifier: Modifier = Modifier,
    navigateToDetails: (String) -> Unit,
    viewModel: MainViewModel
) {
    viewModel.updateCurrentScreen(Screens.TRAILS)
    val activity = (LocalContext.current) as Activity

    val trails: List<TrailResponseItem> = viewModel.trails.collectAsState().value

    BackHandler {
        activity.finish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
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

            is State.Success -> trails.let {
                TrailsList(
                    trails = it,
                    navigateToDetails,
                    viewModel)
            }
            is State.Error -> {
                viewModel.showSnackBar()
            }
        }
    }
}