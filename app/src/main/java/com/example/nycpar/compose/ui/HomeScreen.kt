package com.example.nycpar.compose.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nycpar.R
import com.example.nycpar.compose.ui.components.DrawerContent
import com.example.nycpar.compose.ui.components.StatusBar
import com.example.nycpar.models.Screens
import com.example.nycpar.ui.theme.PatuaOneFontFamily
import com.example.nycpar.ui.theme.Primary
import com.example.nycpar.viewmodels.MainViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToScreen: (Screens) -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    StatusBar()

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

    val currentScreen = viewModel.currentScreen.screen

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .background(Primary),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(
                    text = currentScreen,
                    fontFamily = PatuaOneFontFamily
                ) },
                navigationIcon = {
                     Icon(
                         imageVector = Icons.Default.Menu,
                         contentDescription = stringResource(id = R.string.menu),
                         modifier = Modifier
                             .padding(horizontal = 8.dp)
                             .clickable(onClick = {
                             scope.launch {
                                 scaffoldState.drawerState.let { drawer ->
                                     if(drawer.isClosed) drawer.open() else drawer.close()
                                 }
                             }
                         })
                     )
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.FavoriteBorder, stringResource(id = R.string.favorite))
                    }
                }
            )
        },
        drawerContent = {
            DrawerContent(
                itemClick = { nextScreen ->
                    //only change screen if not on current screen
                    (currentScreen != nextScreen).let {
                        when(nextScreen) {
                            Screens.TRAILS.screen -> navigateToScreen(Screens.TRAILS)
                            Screens.FAVORITES.screen -> navigateToScreen(Screens.FAVORITES)
                        }
                    }
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                }
            )
        }
    ) {
        when(currentScreen) {
            Screens.TRAILS.screen -> {
                TrailsScreen(
                    navigateToScreen = navigateToScreen,
                    viewModel = viewModel
                )
            }
        }
    }
}