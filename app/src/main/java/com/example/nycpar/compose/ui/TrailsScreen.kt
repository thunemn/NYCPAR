package com.example.nycpar.compose.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.nycpar.R
import com.example.nycpar.compose.ui.components.DrawerContent
import com.example.nycpar.compose.ui.components.NYCTopAppBar
import com.example.nycpar.compose.ui.components.StatusBar
import com.example.nycpar.models.Screens
import com.example.nycpar.ui.theme.Accent
import com.example.nycpar.ui.theme.PatuaOneFontFamily
import com.example.nycpar.ui.theme.Primary
import com.example.nycpar.ui.theme.PrimaryDark
import com.example.nycpar.viewmodels.MainViewModel
import kotlinx.coroutines.launch
import kotlin.math.log

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TrailsScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    viewModel.updateCurrentScreen(Screens.TRAILS)

    //show only trail screen related data - custom list
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
    ) {
        Text(
            text = "${viewModel.trails?.size ?: "null"}",
            color = Color.Black,
            fontSize = dimensionResource(id = R.dimen.splash_name_textSize).value.sp,
            fontFamily = PatuaOneFontFamily,
        )
    }
}