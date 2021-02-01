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

package com.fred.earthquaketracker

import com.fred.earthquaketracker.database.earthquakes.EarthquakeSpot
import com.fred.earthquaketracker.features.home.models.EarthquakeSpotModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EarthquakeSpotModelTest {

    lateinit var mockEarthquakeSpotModel: EarthquakeSpotModel

    lateinit var mockEarthquakeSpot: EarthquakeSpot

    @Before
    fun setUp() {
        mockEarthquakeSpotModel = EarthquakeSpotModel(
            "",
            0.5f,
            10f,
            "us",
            "eqid",
            8f,
            12f,
            1
        )

        mockEarthquakeSpot = EarthquakeSpot(
            "",
            0.5f,
            10f,
            "us",
            "eqid",
            8f,
            12f
        ).also { it.id = 1 }
    }

    @Test
    fun `GIVEN earthquakeSpotModel THEN verify transform to room database data model`() {
        val testEarthquakeSpot = EarthquakeSpot.fromModel(
            mockEarthquakeSpotModel.id ?: -1,
            mockEarthquakeSpotModel
        )

        assert(isEqual(testEarthquakeSpot, mockEarthquakeSpotModel))
    }

    @Test
    fun `GIVEN earthquakeSpot THEN verify transform back to earthquakeSpotModel`() {
        val testEarthquakeSpotModel = mockEarthquakeSpot.toModel()

        assert(isEqual(mockEarthquakeSpot, testEarthquakeSpotModel))
    }

    private fun isEqual(o1: EarthquakeSpot, o2: EarthquakeSpotModel): Boolean {
        o1.run {
            return datetime == o2.datetime &&
                    depth == o2.depth &&
                    lng == o2.lng &&
                    src == o2.src &&
                    eqid == o2.eqid &&
                    magnitude == o2.magnitude &&
                    lat == o2.lat &&
                    id == o2.id
        }


    }
}