package com.fred.earthquaketracker.dagger.viewmodel

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
interface ViewModelProviderFactoryModule {

    @Binds
    fun bindViewModelProviderFactory(factory: ProvidingViewModelFactory): ViewModelProvider.Factory
}

