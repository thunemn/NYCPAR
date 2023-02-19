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
import com.example.nycpar.compose.ui.*
import com.example.nycpar.viewmodels.MainViewModel

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mainViewModel: MainViewModel = viewModel(),
    startDestination: String = "splash",
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("splash") {
            SplashScreen(
                navigateToHome = {
                    navController.navigate("trails")
                }
            )
        }
        composable("trails") {
            TrailsScreen(
                navigateToDetails = { parkName ->
                    navController.navigate("details?parkName=${parkName}")
                },
                viewModel = mainViewModel
            )
        }
        composable("favorite") {
            FavoritesScreen(
                viewModel = mainViewModel
            )
        }
        composable(
            route = "details?parkName={parkName}",
            arguments = listOf(
                navArgument("parkName") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backstackEntry ->
            Log.d(TAG, "park name = ${backstackEntry.arguments?.getString("parkName")}")
            DetailsScreen(
                parkName = backstackEntry.arguments?.getString("parkName") ?: "",
                viewModel = mainViewModel
            )
        }
    }
}