package com.example.nycpar.compose.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nycpar.models.Screens
import com.example.nycpar.viewmodels.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TrailsScreen(
    modifier: Modifier = Modifier,
    navigateToScreen: (Screens) -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    //show only trail screen related data - custom list
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Cyan)
    )
}