package com.example.nycpar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.nycpar.compose.NavigationHost
import com.example.nycpar.compose.ui.HomeScreen
import com.example.nycpar.ui.theme.AppTheme
import com.example.nycpar.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                HomeScreen()
            }
        }
    }
}