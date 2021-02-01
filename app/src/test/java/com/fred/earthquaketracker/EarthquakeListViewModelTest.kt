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
import com.fred.earthquaketracker.database.earthquakes.EarthquakeSpot
import com.fred.earthquaketracker.features.home.repository.EarthquakeRepository
import com.fred.earthquaketracker.features.home.repository.EarthquakeResponse
import com.fred.earthquaketracker.features.home.viewmodels.EarthquakeListViewModel
import com.fred.earthquaketracker.features.home.viewmodels.HomeNavigationEvent
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EarthquakeListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var testViewModel: EarthquakeListViewModel

    @RelaxedMockK
    lateinit var testEarthquakeRepository: EarthquakeRepository

    @RelaxedMockK
    lateinit var mockLoadingStateObserver: Observer<Pair<Boolean, String?>>

    @RelaxedMockK
    lateinit var mockEarthquakeListObserver: Observer<List<EarthquakeSpot>>

    @RelaxedMockK
    lateinit var mockHomeNavigationEventObserver: Observer<HomeNavigationEvent>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        testViewModel = EarthquakeListViewModel(testEarthquakeRepository)

        testViewModel.loadingStateLiveData.observeForever(mockLoadingStateObserver)

        testViewModel.homeNavigationLiveData.observeForever(mockHomeNavigationEventObserver)

        testViewModel.earthquakeListLiveData.observeForever(mockEarthquakeListObserver)
    }

    @Test
    fun `GIVEN service return error THEN verify the loading state should stop load and contain messages`() =
        runBlocking {
            val testMessage = "error"

            every {
                testEarthquakeRepository.refreshList(
                    any(), any(), any(), any(), any(), any()
                )
            } returns EarthquakeResponse.EarthquakeErrorResponse(testMessage)

            testViewModel.fetchEarthquakeList()

            verify { mockLoadingStateObserver.onChanged(Pair(false, testMessage)) }
        }

    @Test
    fun `GIVEN navigation event is OnListITemClickedEvent THEN verify homeNavigationLiveData is changed`() =
        runBlocking {
            testViewModel.navigationEvent(event = HomeNavigationEvent.OnListItemClickedEvent)

            verify { mockHomeNavigationEventObserver.onChanged(HomeNavigationEvent.OnListItemClickedEvent) }
        }

    @Test
    fun `GIVEN service return success THEN verify the notification should not be updated`() =

        runBlocking {
            val testEarthquake = EarthquakeSpot("", 0f, 0f, "us", "", 0f, 0f)
            every { testEarthquakeRepository.earthquakeListLiveData.observeForever(any()) } answers {
                (firstArg() as Observer<List<EarthquakeSpot>>).onChanged(listOf(testEarthquake))
                nothing
            }
            coEvery {
                testEarthquakeRepository.refreshList(
                    any(), any(), any(), any(), any(), any()
                )
            } returns EarthquakeResponse.Success

            testViewModel.fetchEarthquakeList()

            verify { mockLoadingStateObserver.onChanged(Pair(false, null)) }
        }
}