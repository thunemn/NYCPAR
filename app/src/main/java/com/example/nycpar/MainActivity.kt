package com.example.nycpar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.nycpar.compose.NavigationHost
import com.example.nycpar.ui.theme.AppTheme
import com.example.nycpar.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                NavigationHost(mainViewModel = viewModel)
            }
        }
    }
}