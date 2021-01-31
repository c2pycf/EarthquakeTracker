package com.fred.earthquaketracker.features.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class EarthquakeSpotViewModel @Inject constructor() : ViewModel() {

    private val _currentPickedEarthquakeLocation = MutableLiveData<Pair<Float, Float>>()
    internal val currentPickedEarthquakeLocation: LiveData<Pair<Float, Float>>
        get() = _currentPickedEarthquakeLocation


    /**
     * Update user picked location for map to locate
     */
    internal fun updateCurrentPickedLocation(lng: Float, lat: Float) =
        viewModelScope.launch(Dispatchers.IO) {
            _currentPickedEarthquakeLocation.postValue(Pair(lat, lng))
        }
}