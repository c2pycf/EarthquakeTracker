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