package com.example.nycpar.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycpar.api.ApiHelper
import com.example.nycpar.api.ApiInterface
import com.example.nycpar.api.ParkResponseItem
import com.example.nycpar.compose.ui.TAG
import com.example.nycpar.models.Screens
import com.example.nycpar.models.Trail
import io.realm.Realm
import io.realm.kotlin.createObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MainViewModel : ViewModel() {

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    private val _currentScreen = MutableStateFlow(Screens.SPLASH)
    val currentScreen = _currentScreen.asStateFlow()

    fun updateCurrentScreen(screen: Screens) {
        _currentScreen.value = screen
    }

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    private val _trails = MutableStateFlow<List<ParkResponseItem>?>(listOf())
    val trails = _trails.asStateFlow()

    private val _isSnackBarShowing = MutableStateFlow(false)
    val isSnackBarShowing = _isSnackBarShowing.asStateFlow()

    fun showSnackBar() {
        _isSnackBarShowing.value = true
    }

    fun hideSnackBar() {
        _isSnackBarShowing.value = false
    }

    fun getTrails() {
        Log.d(TAG, "getTrails()")
        val parksApi = ApiHelper.getInstance().create(ApiInterface::class.java)
        viewModelScope.launch {
            _state.value = State.Loading
            val response = parksApi.getParks()
            Log.d(TAG, "response code: ${response.code()}")
            withContext(Dispatchers.Default) {
                try {
                    if(response.isSuccessful) {
                        Log.d(TAG, "success!")
                        _state.value = State.Success

                        val allTrails = response.body()
                        _trails.value = allTrails?.distinctBy { it.trailName }
                    }
                    else {
                        Log.d(TAG, "error: ${response.errorBody()}")
                        _state.value = State.Error(response.code(), "${response.errorBody()?.string()}")
                    }
                } catch (e: HttpException) {
                    Log.d(TAG, "Http exception: ${e.message}")
                    _state.value = State.Error(response.code(), "${e.message}")
                } catch (e: Throwable) {
                    Log.d(TAG, "Unknown exception: ${e.message}")
                    _state.value = State.Error(response.code(), "${e.message}")
                }
            }
        }
    }

    fun addTrailToFavorites(trailItem: ParkResponseItem) {
        viewModelScope.launch {
            realm.executeTransaction { r ->
                r.copyToRealmOrUpdate(trailItem)
                Log.d(TAG, "Favorite trail: ${trailItem.parkName}")
            }
        }

    }

    fun isTrailFavorite(trailName: String): Boolean {
        return false
    }
}

sealed class State {
    object Loading: State()
    object Success: State()
    data class Error(val errorCode: Int, val error: String): State()
}