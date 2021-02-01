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