package com.fred.earthquaketracker.dagger

import android.content.Context
import com.fred.earthquaketracker.dagger.features.FeatureComponents
import com.fred.earthquaketracker.dagger.viewmodel.ViewModelProviderFactoryModule
import com.fred.earthquaketracker.database.DatabaseModule
import com.fred.earthquaketracker.features.home.di.HomeComponent
import com.fred.earthquaketracker.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


/**
 * Declare top level feature and util module here
 */
@Singleton
@Component(
    modules = [
        ViewModelProviderFactoryModule::class,
        FeatureComponents::class,
        NetworkModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun homeComponent(): HomeComponent.Factory

}