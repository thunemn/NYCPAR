package com.example.nycpar.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.nycpar.models.Screens
import com.example.nycpar.ui.theme.Primary
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel : ViewModel() {

//    var backgroundColor: Color by mutableStateOf(Primary)
//        private set
//
//    fun changeBackgroundColor(color: Color) {
//        backgroundColor = color
//    }
    var currentScreen: Screens by mutableStateOf(Screens.SPLASH)
        private set

    fun updateCurrentScreen(screen: Screens) {
        currentScreen = screen
    }
}