package com.fred.earthquaketracker.database

import android.content.Context
import com.fred.earthquaketracker.database.earthquakes.EarthquakesDao
import com.fred.earthquaketracker.database.earthquakes.EarthquakesDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideUserDB(context: Context): EarthquakesDao = EarthquakesDatabase.getInstance(context).earthquakesDao

}