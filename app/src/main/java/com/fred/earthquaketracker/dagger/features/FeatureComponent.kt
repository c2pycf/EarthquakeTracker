package com.fred.earthquaketracker.dagger.features

import com.fred.earthquaketracker.features.home.di.HomeComponent
import dagger.Module

/**
 * Declare feature components here
 */
@Module(
    subcomponents = [
        HomeComponent::class
    ]
)
class FeatureComponents