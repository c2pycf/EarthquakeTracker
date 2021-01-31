package com.fred.earthquaketracker.network

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideRetrofitService() =

         Retrofit.Builder()
            .baseUrl(BASE_ULT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    companion object{
        private const val BASE_ULT = "http://api.geonames.org/"
    }
}