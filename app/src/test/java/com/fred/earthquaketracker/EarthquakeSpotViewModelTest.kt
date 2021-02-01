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