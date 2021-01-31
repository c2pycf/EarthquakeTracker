package com.fred.earthquaketracker.features.home.repository

import com.fred.earthquaketracker.features.home.models.EarthquakeResultModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EarthquakeService {

    /**
     * Earthquake list end point
     */
    @GET(EARTHQUAKES_JSON)
    fun getEarthquakeList(
        @Query(QUERY_FORMATTED) format: Boolean,
        @Query(QUERY_NORTH) north: Float,
        @Query(QUERY_SOUTH) south: Float,
        @Query(QUERY_EAST) east: Float,
        @Query(QUERY_WEST) west: Float,
        @Query(QUERY_USERNAME) username: String
    ): Call<EarthquakeResultModel>

    companion object {
        private const val EARTHQUAKES_JSON =
            "/earthquakesJSON"
        private const val QUERY_FORMATTED = "formatted"
        private const val QUERY_NORTH = "north"
        private const val QUERY_SOUTH = "south"
        private const val QUERY_EAST = "east"
        private const val QUERY_WEST = "west"
        private const val QUERY_USERNAME = "username"
    }

}

