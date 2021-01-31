package com.fred.earthquaketracker

import android.app.Application
import com.fred.earthquaketracker.dagger.AppComponent
import com.fred.earthquaketracker.dagger.DaggerAppComponent

open class MyApplication : Application(){

    //Initiate App configuration
    override fun onCreate() {
        super.onCreate()
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}

