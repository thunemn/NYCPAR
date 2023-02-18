package com.example.nycpar.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nycpar.compose.ui.TrailsScreen
import com.example.nycpar.compose.ui.SplashScreen
import com.example.nycpar.viewmodels.MainViewModel

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "splash",
    mainViewModel: MainViewModel = viewModel()) {

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
                navigateToScreen = {screen ->

                },
                viewModel = mainViewModel
            )
        }
        composable("favorite") {
//            FavoritesScreen(
//                navigateToScreen = {screen ->
//
//                },
//                viewModel = mainViewModel
//            )
        }
        composable("details") {
//            DetailsScreen(
//                navigateToScreen = {screen ->
//
//                },
//                viewModel = mainViewModel
//            )
        }
    }
}