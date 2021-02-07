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

package com.fred.earthquaketracker.features.home.repository.remote

import com.fred.earthquaketracker.features.home.models.EarthquakeResultModel
import retrofit2.Call
import retrofit2.Response
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

    @GET(EARTHQUAKES_JSON)
    suspend fun getEarthquakeListSuspend(
        @Query(QUERY_FORMATTED) format: Boolean,
        @Query(QUERY_NORTH) north: Float,
        @Query(QUERY_SOUTH) south: Float,
        @Query(QUERY_EAST) east: Float,
        @Query(QUERY_WEST) west: Float,
        @Query(QUERY_USERNAME) username: String
    ): Response<EarthquakeResultModel>

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

