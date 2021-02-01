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

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fred.earthquaketracker.database.earthquakes.EarthquakeSpot
import com.fred.earthquaketracker.database.earthquakes.EarthquakesDao
import com.fred.earthquaketracker.features.home.models.EarthquakeResultModel
import com.fred.earthquaketracker.features.home.models.EarthquakeSpotModel
import com.fred.earthquaketracker.features.home.repository.EarthquakeRepository
import com.fred.earthquaketracker.features.home.repository.EarthquakeResponse
import com.fred.earthquaketracker.features.home.repository.EarthquakeService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class EarthquakeRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var testRepository: EarthquakeRepository

    @RelaxedMockK
    lateinit var mockEarthquakeListObserver: Observer<List<EarthquakeSpot>>

    @RelaxedMockK
    lateinit var mockEarthquakeService: EarthquakeService

    @RelaxedMockK
    lateinit var mockEarthquakeDao: EarthquakesDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mockkStatic(Looper::class)

        testRepository = EarthquakeRepository(mockEarthquakeService, mockEarthquakeDao)

        testRepository.earthquakeListLiveData.observeForever(mockEarthquakeListObserver)

        every { Looper.myLooper() == Looper.getMainLooper() } returns false
    }

    @Test
    fun `GIVEN fetch remote service success THEN verify success response is returned`() =
        runBlocking {
            val testEarthquakeSpotModel = EarthquakeSpotModel("", 0f, 0f, "us", "", 0f, 0f, 1)
            val mockResponse = EarthquakeResultModel(listOf(testEarthquakeSpotModel))

            every {
                mockEarthquakeService.getEarthquakeList(
                    any(), any(), any(), any(), any(), any()
                ).execute()
            } returns Response.success(mockResponse)

            val result = testRepository.refreshList(
                true, 0f, 0f, 0f, 0f, ""
            )

            assert(result is EarthquakeResponse.Success)
        }

    @Test
    fun `GIVEN earthquake list from service WHEN fetch list success THEN verify list is insert to DB`() =
        runBlocking {
            val testEarthquakeSpotModel = EarthquakeSpotModel("", 0f, 0f, "us", "", 0f, 0f, 1)
            val mockResponse = EarthquakeResultModel(listOf(testEarthquakeSpotModel))
            val testEarthquakeSpotModelList =
                listOf(testEarthquakeSpotModel).mapIndexed { index, earthquakeSpotModel ->
                    EarthquakeSpot.fromModel(
                        id = index,
                        earthquakeSpot = earthquakeSpotModel
                    )

                }

            every {
                mockEarthquakeService.getEarthquakeList(
                    any(), any(), any(), any(), any(), any()
                ).execute()
            } returns Response.success(mockResponse)

            testRepository.refreshList(
                true, 0f, 0f, 0f, 0f, ""
            )

            verify { mockEarthquakeDao.insertAll(testEarthquakeSpotModelList) }
        }

    @Test
    fun `GIVEN fetching from remote service is Failed THEN verify error response`() =
        runBlocking {

            val body = ResponseBody.create(
                MediaType.parse("mock error"),
                "error"
            )
            every {
                mockEarthquakeService.getEarthquakeList(
                    any(), any(), any(), any(), any(), any()
                ).execute()
            } returns Response.error(401, body)

            val result = testRepository.refreshList(
                true, 0f, 0f, 0f, 0f, ""
            )

            assert(result is EarthquakeResponse.EarthquakeErrorResponse)
        }
}