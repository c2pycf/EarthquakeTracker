package com.fred.earthquaketracker.features.home.di

import com.fred.earthquaketracker.dagger.ActivityScope
import com.fred.earthquaketracker.features.home.controller.MainActivity
import com.fred.earthquaketracker.features.home.ui.EarthquakeSpotListFragment
import com.fred.earthquaketracker.features.home.ui.EarthquakeSpotMapFragment
import dagger.Subcomponent
import dagger.android.ContributesAndroidInjector

@ActivityScope
@Subcomponent(modules = [HomeProviderModule::class])
interface HomeComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeComponent
    }

    @ContributesAndroidInjector
    fun inject(activity: MainActivity)
    @ContributesAndroidInjector
    fun inject(fragment: EarthquakeSpotListFragment)
    @ContributesAndroidInjector
    fun inject(fragment: EarthquakeSpotMapFragment)
}