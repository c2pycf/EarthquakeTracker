package com.fred.earthquaketracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fred.earthquaketracker.features.home.viewmodels.EarthquakeSpotViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EarthquakeSpotViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var testViewModel: EarthquakeSpotViewModel

    @RelaxedMockK
    lateinit var mockCurrentPickedEarthquakeLocationObserver: Observer<Pair<Float, Float>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        testViewModel = EarthquakeSpotViewModel()

        testViewModel.currentPickedEarthquakeLocation.observeForever(
            mockCurrentPickedEarthquakeLocationObserver
        )

    }

    @Test
    fun `GIVEN lng and lat WHEN update the current location THEN verify the location livedata should change`() =
        runBlocking {
            val testLng = 10f
            val testLat = 10f

            testViewModel.updateCurrentPickedLocation(testLng, testLat)

            verify { mockCurrentPickedEarthquakeLocationObserver.onChanged(Pair(10f, 10f)) }
        }
}