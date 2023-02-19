package com.example.nycpar.compose.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.nycpar.compose.ui.components.DrawerContent
import com.example.nycpar.compose.ui.components.NYCTopAppBar
import com.example.nycpar.compose.ui.components.StatusBar
import com.example.nycpar.models.Screens
import com.example.nycpar.ui.theme.Black
import com.example.nycpar.ui.theme.Primary
import com.example.nycpar.ui.theme.PrimaryDark
import com.example.nycpar.viewmodels.MainViewModel
import kotlinx.coroutines.launch
import kotlin.math.log

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    parkName: String,
    viewModel: MainViewModel = viewModel()
) {
    viewModel.updateCurrentScreen(Screens.DETAILS)

    //show only trail screen related data - custom list
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Text(
            text = parkName,
            color = Black,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Start,
        )
    }
}