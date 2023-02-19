package com.example.nycpar.compose.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nycpar.compose.NavigationHost
import com.example.nycpar.compose.ui.components.DrawerContent
import com.example.nycpar.compose.ui.components.NYCTopAppBar
import com.example.nycpar.compose.ui.components.StatusBar
import com.example.nycpar.ui.theme.Primary
import com.example.nycpar.viewmodels.MainViewModel
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.launch

const val TAG = "TAG"

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    val context = LocalContext.current
    
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

    val currentScreen: String = viewModel.currentScreen.collectAsState().value.screen

    val navController = rememberNavController()
    val showTopBar = when (currentRoute(navController = navController)) {
        "trails" -> true
        "favorite" -> true
        else -> false
    }

    Realm.init(context)
    Realm.setDefaultConfiguration(
        RealmConfiguration.Builder()
            .allowWritesOnUiThread(true)
            .deleteRealmIfMigrationNeeded()
            .build()
    )

    LaunchedEffect(context) {
        viewModel.getTrails()
        viewModel.isSnackBarShowing.collect {
            if(it) {
                scope.launch {
                    val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                        message = "Error loading park data.",
                        actionLabel = "Try again",

                    )
                    when(snackbarResult) {
                        SnackbarResult.ActionPerformed -> viewModel.getTrails()
                        SnackbarResult.Dismissed -> Log.d(TAG, "Snackbar dismissed")
                    }
                }
            }
        }
    }

    StatusBar()

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .background(Primary),
        scaffoldState = scaffoldState,
        topBar = {
            if(showTopBar) {
                NYCTopAppBar(
                    currentScreen,
                    scaffoldState,
                    onFavoriteClick = {

                    })
            }
        },
        drawerContent = {
            if(showTopBar) {
                DrawerContent(
                    itemClick = { nextScreen ->
                        Log.d(TAG, nextScreen)
                        //only change screen if not on current screen
                        (currentScreen != nextScreen).let {
                            navController.navigate(nextScreen) {
                                popUpTo("trails")
                                launchSingleTop = true
                                restoreState = true
                            }
                        }

                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }
                )
            }
        }
    ) {
        NavigationHost(
            navController = navController,
            mainViewModel = viewModel)
    }
}

@Composable
public fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}