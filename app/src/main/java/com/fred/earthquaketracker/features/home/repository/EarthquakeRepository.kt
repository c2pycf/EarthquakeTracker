package com.fred.earthquaketracker.features.home.repository

import android.os.Looper
import android.util.Log
import androidx.annotation.WorkerThread
import com.fred.earthquaketracker.database.earthquakes.EarthquakeSpot
import com.fred.earthquaketracker.database.earthquakes.EarthquakesDao
import javax.inject.Inject


/**
 * Handle remote service requests and save list to local repository
 */
class EarthquakeRepository @Inject constructor(
    private val earthquakeService: EarthquakeService,
    private val earthquakesDao: EarthquakesDao
) {

    internal val earthquakeListLiveData = earthquakesDao.getAllEarthquakeSpotsLiveData

    /**
     * Fetch earthquake list from service through worker thread
     * Can be optimized with flow and emit the value into db
     */
    @WorkerThread
    fun refreshList(
        format: Boolean,
        north: Float,
        south: Float,
        east: Float,
        west: Float,
        username: String
    ): EarthquakeResponse {
        try {
            checkBackgroundThread()
            val result = earthquakeService.getEarthquakeList(
                format, north, south, east, west, username
            ).execute()
            if (result.isSuccessful) {
                result.body()?.earthquakes?.mapIndexed { index, earthquakeSpotModel ->
                    EarthquakeSpot.fromModel(
                        id = index,
                        earthquakeSpot = earthquakeSpotModel
                    )
                }?.let {
                    earthquakesDao.insertAll(it)
                }
                return EarthquakeResponse.Success
            }
            return EarthquakeResponse.EarthquakeErrorResponse(result.message())
        } catch (error: Exception) {
            return EarthquakeResponse.EarthquakeErrorResponse(message = error.message.toString())
        }
    }

    private fun checkBackgroundThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw IllegalStateException("THIS METHOD SHOULD NOT RUN ON MAIN THREAD")
        }
    }
}

sealed class EarthquakeResponse {
    data class EarthquakeErrorResponse(val message: String?) : EarthquakeResponse()
    object Success : EarthquakeResponse()
}