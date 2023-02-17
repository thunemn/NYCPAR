package com.example.nycpar.compose.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.nycpar.models.Screens
import com.example.nycpar.viewmodels.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nycpar.R
import com.example.nycpar.ui.theme.Accent
import com.example.nycpar.ui.theme.PatuaOneFontFamily
import com.example.nycpar.ui.theme.Primary
import com.example.nycpar.ui.theme.Red

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToScreen: (Screens) -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxWidth()
            .background(Primary)
    ) {
        Text(
            text = "Testing 123...",
            color = Red,
            fontSize = dimensionResource(id = R.dimen.splash_name_textSize).value.sp,
            fontFamily = PatuaOneFontFamily,
            textAlign = TextAlign.Center,
        )
    }
}