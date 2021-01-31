package com.fred.earthquaketracker

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.room.Ignore
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.fred.earthquaketracker.features.home.ui.EarthquakeSpotListFragment
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Issue with dagger fragment, can be fixed in the future
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
 class EarthquakeListViewTest {

    @Test
    @Ignore
    fun scrollToItemBelowFold_checkItsText() {
        val scenario = launchFragmentInContainer<EarthquakeSpotListFragment>()
        val testString = "Magnitude"
        onView(withText(testString)).check(matches(isDisplayed()))
    }
}