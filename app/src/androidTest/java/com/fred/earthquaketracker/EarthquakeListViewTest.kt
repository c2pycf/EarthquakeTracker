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