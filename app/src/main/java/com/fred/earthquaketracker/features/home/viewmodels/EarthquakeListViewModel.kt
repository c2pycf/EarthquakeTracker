package com.fred.earthquaketracker.features.home.viewmodels

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fred.earthquaketracker.common.SingleLiveEvent
import com.fred.earthquaketracker.features.home.repository.EarthquakeRepository
import com.fred.earthquaketracker.features.home.repository.EarthquakeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Modifier
import javax.inject.Inject

class EarthquakeListViewModel @Inject constructor(
    private val earthquakeRepository: EarthquakeRepository
) : ViewModel() {

    internal val earthquakeListLiveData = earthquakeRepository.earthquakeListLiveData

    private val _loadingSateLiveData = MutableLiveData<Pair<Boolean, String?>>()
    internal val loadingStateLiveData: LiveData<Pair<Boolean, String?>>
        get() = _loadingSateLiveData

    private val _homeNavigationLiveData = SingleLiveEvent<HomeNavigationEvent>()
    internal val homeNavigationLiveData: LiveData<HomeNavigationEvent>
        get() = _homeNavigationLiveData


    /**
     * Fetch list in IO thread with fixed query for now. Can be optimized
     */
    @VisibleForTesting(otherwise = Modifier.PRIVATE)
    internal suspend fun fetchEarthquakeList() = withContext(Dispatchers.IO) {
        when (val result = earthquakeRepository.refreshList(
            format = true,
            north = NORTH,
            south = SOUTH,
            east = EAST,
            west = WEST,
            username = USERNAME
        )) {
            is EarthquakeResponse.EarthquakeErrorResponse -> {
                _loadingSateLiveData.postValue(
                    Pair(
                        false,
                        result.message
                    )
                )
            }
            else -> {
                _loadingSateLiveData.postValue(
                    Pair(
                        false,
                        null
                    )
                )
            }
        }
    }

    /**
     * Load earthquake list
     */
    fun loadEarthquakeList() = viewModelScope.launch {
        _loadingSateLiveData.value = Pair(true, null)
        fetchEarthquakeList()

    }

    /**
     * Check current list is not empty then fetch data
     */
    fun reloadEarthquakeList() = viewModelScope.launch {
        _loadingSateLiveData.value = Pair(true, null)
        if (earthquakeListLiveData.value?.isEmpty() != false) {
            fetchEarthquakeList()
        } else {
            _loadingSateLiveData.value = Pair(false, null)
        }
    }

    /**
     * Navigation event. Currently only item click event
     */

    fun navigationEvent(event: HomeNavigationEvent) {
        when (event) {
            is HomeNavigationEvent.OnListItemClickedEvent -> {
                _homeNavigationLiveData.value = HomeNavigationEvent.OnListItemClickedEvent
            }
        }

    }

    fun showNotification(message: String) = viewModelScope.launch {
        _loadingSateLiveData.value = Pair(false, message)
    }

    companion object {
        //south=-9.9&east=-22.4&west=55.2
        const val USERNAME = "mkoppelman" //Use the name given from url for now
        const val NORTH = 44.1f
        const val SOUTH = -9.9f
        const val EAST = -22f
        const val WEST = 55.2f
    }
}

sealed class HomeNavigationEvent {
    object OnListItemClickedEvent : HomeNavigationEvent()
}