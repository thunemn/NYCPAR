package com.example.nycpar.compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nycpar.models.Screens
import com.example.nycpar.ui.theme.Primary
import com.example.nycpar.viewmodels.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen(
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
            NYCTopAppBar(
                currentScreen,
                scaffoldState,
                onFavoriteClick = {

                })
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
        //show only trail screen related data - custom list
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan)
        )
    }
}