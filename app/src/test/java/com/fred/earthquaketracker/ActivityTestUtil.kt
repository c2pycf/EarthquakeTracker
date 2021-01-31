package com.fred.earthquaketracker

import org.robolectric.Robolectric

object ActivityTestUtil {
    fun buildThemeActivity(): TestActivity {
        return Robolectric.buildActivity(TestActivity::class.java).create().start().resume().visible().get().also{
            it.setTheme(R.style.Theme_EarthquakeTracker)
            it.setVisible(true)
        }
    }
}