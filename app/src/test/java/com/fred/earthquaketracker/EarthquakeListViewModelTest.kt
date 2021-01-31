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
            val testUser = EarthquakeSpot("", 0f, 0f, "us", "", 0f, 0f)
            every { testEarthquakeRepository.earthquakeListLiveData.observeForever(any()) } answers {
                (firstArg() as Observer<List<EarthquakeSpot>>).onChanged(listOf(testUser))
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