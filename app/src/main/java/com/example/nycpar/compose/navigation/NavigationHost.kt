package com.example.nycpar.compose

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nycpar.compose.navigation.NavigationConstants
import com.example.nycpar.compose.ui.*
import com.example.nycpar.viewmodels.MainViewModel

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mainViewModel: MainViewModel = viewModel(),
    startDestination: String = NavigationConstants.SPLASH,
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationConstants.SPLASH) {
            SplashScreen(
                navigateToHome = {
                    navController.navigate(NavigationConstants.TRAILS)
                }
            )
        }
        composable(NavigationConstants.TRAILS) {
            TrailsScreen(
                navigateToDetails = { parkName ->
                    navController.navigate("${NavigationConstants.DETAILS_NAVIGATE}${parkName}")
                },
                viewModel = mainViewModel
            )
        }
        composable(NavigationConstants.FAVORITE) {
            FavoritesScreen(
                navigateToDetails = { parkName ->
                    navController.navigate("${NavigationConstants.DETAILS_NAVIGATE}${parkName}")
                },
                viewModel = mainViewModel
            )
        }
        composable(
            route = NavigationConstants.DETAILS_ROUTE,
            arguments = listOf(
                navArgument(NavigationConstants.PARK_NAME) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backstackEntry ->
            Log.d(TAG, "park name = ${backstackEntry.arguments?.getString(NavigationConstants.PARK_NAME)}")
            DetailsScreen(
                parkName = backstackEntry.arguments?.getString(NavigationConstants.PARK_NAME) ?: "",
                viewModel = mainViewModel
            )
        }
    }
}