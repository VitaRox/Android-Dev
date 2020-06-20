package com.example.zipcodebuddy.api

import com.squareup.moshi.Json

data class Forecast(val temp: Float)
data class Coordinates(val lat: Float, val lon: Float)

data class CurrentWeather(
    val name: String,
    val coord: Coordinates,
    // Json response field called main, map to forecast property;
    @field:Json(name = "main") val forecast: Forecast
)