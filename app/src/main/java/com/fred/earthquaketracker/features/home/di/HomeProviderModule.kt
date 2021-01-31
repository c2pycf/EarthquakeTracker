package com.fred.earthquaketracker.features.home.di

import androidx.lifecycle.ViewModel
import com.fred.earthquaketracker.dagger.viewmodel.ViewModelKey
import com.fred.earthquaketracker.features.home.repository.EarthquakeService
import com.fred.earthquaketracker.features.home.viewmodels.EarthquakeSpotViewModel
import com.fred.earthquaketracker.features.home.viewmodels.EarthquakeListViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module
internal abstract class HomeProviderModule {

    @Binds
    @IntoMap
    @ViewModelKey(EarthquakeListViewModel::class)
    internal abstract fun provideEarthquakeListViewModel(userViewModel: EarthquakeListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EarthquakeSpotViewModel::class)
    internal abstract fun provideEarthquakeSpotViewModel(userViewModel: EarthquakeSpotViewModel): ViewModel

    companion object {
        @Provides
        fun provideEarthquakeService(retrofit: Retrofit): EarthquakeService {
            return retrofit.create(EarthquakeService::class.java)
        }
    }
}