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

import android.content.res.ColorStateList
import android.os.Build
import android.view.LayoutInflater
import android.widget.TextView
import com.fred.earthquaketracker.common.extensions.resolveColorReference
import com.fred.earthquaketracker.features.home.models.EarthquakeSpotModel
import com.fred.earthquaketracker.features.home.views.EarthquakeSpotViewHolder
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(
    RobolectricTestRunner::class
)
@Config(sdk = [Build.VERSION_CODES.P])
class EarthquakeSpotViewHolderTest {

    private val activity: TestActivity by lazy {
        ActivityTestUtil.buildThemeActivity()
    }

    @Test
    fun `GIVEN earthquakeSpot item view`() {
        val view = LayoutInflater.from(activity)
            .inflate(R.layout.item_earthquake_spot, null, false)
        val itemView = EarthquakeSpotViewHolder(view, { }, true)

        val testEarthquake = EarthquakeSpotModel("", 0f, 0f, "us", "", 9f, 0f, 0)

        itemView.onBindView(testEarthquake)

        val color = view.findViewById<TextView>(R.id.magnitude_tv).textColors

        val target = ColorStateList.valueOf(view.context.resolveColorReference(R.attr.colorError))

        assert(color == target)

    }
}