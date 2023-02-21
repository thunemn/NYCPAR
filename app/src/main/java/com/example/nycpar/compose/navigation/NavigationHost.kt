package com.example.nycpar.compose

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nycpar.compose.navigation.NavigationConstants
import com.example.nycpar.compose.ui.*
import com.example.nycpar.viewmodels.MainViewModel

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mainViewModel: MainViewModel,
    startDestination: String = NavigationConstants.SPLASH_ROUTE,
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationConstants.SPLASH_ROUTE) {
            SplashScreen(
                navigateToHome = {
                    navController.navigate(NavigationConstants.TRAILS_ROUTE)
                },
                viewModel = mainViewModel
            )
        }
        composable(NavigationConstants.TRAILS_ROUTE) {
            TrailsScreen(
                navigateToDetails = { primaryKey ->
                    navController.navigate("${NavigationConstants.DETAILS_NAVIGATE}${primaryKey}")
                },
                viewModel = mainViewModel
            )
        }
        composable(NavigationConstants.FAVORITE_ROUTE) {
            FavoritesScreen(
                navigateToDetails = { primaryKey ->
                    navController.navigate("${NavigationConstants.DETAILS_NAVIGATE}${primaryKey}")
                },
                viewModel = mainViewModel
            )
        }
        composable(
            route = NavigationConstants.DETAILS_ROUTE,
            arguments = listOf(
                navArgument(NavigationConstants.PRIMARY_KEY) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backstackEntry ->
            DetailsScreen(
                viewModel = mainViewModel
            )
        }
    }
}