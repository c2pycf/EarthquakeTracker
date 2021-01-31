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