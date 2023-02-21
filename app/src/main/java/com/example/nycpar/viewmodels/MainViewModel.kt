package com.example.nycpar.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycpar.api.ApiHelper
import com.example.nycpar.api.ApiInterface
import com.example.nycpar.api.TrailResponseItem
import com.example.nycpar.compose.ui.TAG
import com.example.nycpar.models.Screens
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    private val _trails = MutableStateFlow<List<TrailResponseItem>>(listOf())
    val trails = _trails.asStateFlow()

    private val _faves = MutableStateFlow<List<TrailResponseItem>>(listOf())
    val faves = _faves.asStateFlow()

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

                        //set primary key
                        var allTrails = response.body()?.apply {
                            forEach { item ->
                                item.trailName?.let {
                                    item.primaryKey = "${item.trailName}/${item.parkName}"
                                }
                            }
                        }
                        allTrails = allTrails?.sortedWith(compareBy( {it.trailName}, {it.parkName} ))

                        allTrails?.let { all ->
                            _trails.value = all
                                .filter { it.primaryKey != null }
                                .distinctBy { it.primaryKey }
                        }
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

    fun addTrailToFavorites(trailItem: TrailResponseItem): Boolean {
        viewModelScope.launch {
            realm.executeTransaction { r ->
                trailItem.isFavorite = true
                r.copyToRealmOrUpdate(trailItem)
                Log.d(TAG, "Favorite trail: ${trailItem.parkName}")
                _trails.value.find { it.primaryKey == trailItem.primaryKey }?.apply {
                    isFavorite = true
                }

                //force MutableStateFlow to emit change
                _trails.update {
                    _trails.value.toMutableList().apply {
                        remove(trailItem)
                        add(trailItem)
                    }
                }

                _faves.update {
                    _faves.value.toMutableList().apply {
                        add(trailItem)
                    }
                }

                true
            }
        }
        return false
    }

    fun removeTrailFromFavorites(trailItem: TrailResponseItem): Boolean {
        val primaryKey = trailItem.primaryKey

        viewModelScope.launch {
            realm.executeTransaction { r ->
                trailItem.isFavorite = false
                realm.where(TrailResponseItem::class.java).equalTo("primaryKey", trailItem.primaryKey)?.findAll()?.deleteAllFromRealm()
                _trails.value.find { it.primaryKey == primaryKey }?.apply {
                    isFavorite = false
                }

                //force MutableStateFlow to emit change
                _trails.update {
                    _trails.value.toMutableList().apply {
                        remove(trailItem)
                        add(trailItem)
                    }
                }

                _faves.update {
                    _faves.value.toMutableList().apply {
                        clear()
                        addAll(getFavoriteTrails())
                    }
                }

                true
            }
        }
        return false
    }

    fun loadFavorites() {
        _faves.update {
            _faves.value.toMutableList().apply {
                addAll(getFavoriteTrails())
            }
        }
    }

    fun getFavoriteTrails(): List<TrailResponseItem> {
        return realm.where(TrailResponseItem::class.java).findAll()
    }

    fun isTrailFavorite(primaryKey: String): Boolean {
        val trail: TrailResponseItem? = realm.where(TrailResponseItem::class.java).equalTo("primaryKey", primaryKey).findFirst()
        return trail?.isFavorite ?: false
    }

    fun getTrailDetails(primaryKey: String): TrailResponseItem? {
        return realm.where(TrailResponseItem::class.java).equalTo("primaryKey", primaryKey).findFirst()
    }
}

sealed class State {
    object Loading: State()
    object Success: State()
    data class Error(val errorCode: Int, val error: String): State()
}