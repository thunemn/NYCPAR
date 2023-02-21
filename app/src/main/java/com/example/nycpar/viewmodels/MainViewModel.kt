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
import io.realm.RealmConfiguration
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

    var allTrails: List<TrailResponseItem>? = listOf()

    private val _trails = MutableStateFlow<List<TrailResponseItem>>(listOf())
    val trails = _trails.asStateFlow()

    private val _faves = MutableStateFlow<List<TrailResponseItem>>(listOf())
    val faves = _faves.asStateFlow()

    private val _isSnackBarShowing = MutableStateFlow(false)
    val isSnackBarShowing = _isSnackBarShowing.asStateFlow()

    fun showSnackBar() {
        _isSnackBarShowing.value = true
    }

    private val _detailsItem = MutableStateFlow<TrailResponseItem?>(null)
    val detailsItem = _detailsItem.asStateFlow()

    init {
        getTrails()
        loadFavorites()
    }

    fun getTrails() {
        Log.d(TAG, "getTrails()")
        val parksApi = ApiHelper.getInstance().create(ApiInterface::class.java)
        viewModelScope.launch {
            _state.value = State.Loading
            val response = parksApi.getParks()
            withContext(Dispatchers.Default) {
                try {
                    if(response.isSuccessful) {
                        _state.value = State.Success

                        //set primary key
                        allTrails = response.body()?.apply {
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

    fun addTrailToFavorites(trailItem: TrailResponseItem) {
        val primaryKey = trailItem.primaryKey

        viewModelScope.launch {
            realm.executeTransaction { r ->
                trailItem.isFavorite = true
                r.copyToRealmOrUpdate(trailItem)
            }
            allTrails?.find { it.primaryKey == primaryKey }?.isFavorite = true

            allTrails?.let { all ->
                _trails.value = _trails.value.map {
                    if(it.primaryKey == primaryKey) all.find { it.primaryKey == primaryKey } ?: it else it
                }
            }
            _faves.update {
                _faves.value.toMutableList().apply {
                    add(trailItem)
                    sortedWith(compareBy({ it.trailName }, { it.parkName }))
                }
            }
        }

        _detailsItem.value = trails.value.find { it.primaryKey == primaryKey }
    }

    fun removeTrailFromFavorites(trailItem: TrailResponseItem) {
        val primaryKey = trailItem.primaryKey
        var itemToRemove: TrailResponseItem? = null

        viewModelScope.launch {
            realm.executeTransaction { r ->
                trailItem.isFavorite = false
                val results = realm.where(TrailResponseItem::class.java).equalTo("primaryKey", primaryKey)?.findFirst()
                itemToRemove = realm.copyFromRealm(results)
                results?.deleteFromRealm()
            }
            allTrails?.find { it.primaryKey == primaryKey }?.isFavorite = false

            allTrails?.let { all ->
                _trails.value = _trails.value.map {
                    if(it.primaryKey == primaryKey) all.find { it.primaryKey == primaryKey } ?: it else it
                }
            }

            _faves.update {
                _faves.value.toMutableList().apply {
                    removeIf {it.primaryKey == primaryKey}
                    sortedWith(compareBy({ it.trailName }, { it.parkName }))
                }
            }
        }

        _detailsItem.value = trails.value.find { it.primaryKey == primaryKey }
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

    fun setDetailsItem(item: TrailResponseItem) {
        _detailsItem.value = item
    }

    fun clearDetailsItem() {
        _detailsItem.value = null
    }
}

sealed class State {
    object Loading: State()
    object Success: State()
    data class Error(val errorCode: Int, val error: String): State()
}