/*
 * MIT License
 *
 * Copyright (c) 2021 Fang Chen
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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