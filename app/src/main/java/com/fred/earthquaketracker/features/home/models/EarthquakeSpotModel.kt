package com.fred.earthquaketracker.features.home.models

data class EarthquakeSpotModel(
    val datetime: String,
    val depth: Float,
    val lng: Float,
    val src: String,
    val eqid: String,
    val magnitude: Float,
    val lat: Float,
    //Add unique id with list position
    val id: Int?
)

data class EarthquakeResultModel(
    val earthquakes: List<EarthquakeSpotModel>?
)