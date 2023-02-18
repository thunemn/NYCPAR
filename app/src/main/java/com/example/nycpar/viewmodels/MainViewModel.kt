package com.example.nycpar.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycpar.api.ApiHelper
import com.example.nycpar.api.ApiInterface
import com.example.nycpar.api.ParkResponseItem
import com.example.nycpar.compose.ui.TAG
import com.example.nycpar.models.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MainViewModel : ViewModel() {

    var currentScreen: Screens by mutableStateOf(Screens.SPLASH)
        private set

    fun updateCurrentScreen(screen: Screens) {
        currentScreen = screen
    }

    var loading: Boolean by mutableStateOf(false)

    var trails: List<ParkResponseItem>? = mutableListOf()

    fun getParks() {
        Log.d(TAG, "getParks()")
        val parksApi = ApiHelper.getInstance().create(ApiInterface::class.java)
        viewModelScope.launch {
            val response = parksApi.getParks()
            Log.d(TAG, "response code: ${response.code()}")
            withContext(Dispatchers.Main) {
                try {
                    loading = false
                    if(response.isSuccessful) {
                        Log.d(TAG, "success!")
                        trails = response.body()
                    }
                    else {
                        Log.d(TAG, "error: ${response.errorBody()}")
                    }
                } catch (e: HttpException) {
                    Log.d(TAG, "Http exception: ${e.message}")
                } catch (e: Throwable) {
                    Log.d(TAG, "Unknown exception: ${e.message}")
                }
            }
        }
    }
}