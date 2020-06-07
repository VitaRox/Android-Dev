package com.example.zipcodebuddy.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

// function to return a valid imp of the interface;
fun createOpenWeatherMapService() : OpenWeatherMapService {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://api.openweathermap.org")
        .build()

    return retrofit.create(OpenWeatherMapService::class.java)
}

// Serve as basis of our Retrofit API service;
interface OpenWeatherMapService {

    @GET("/data/2.5/weather")
    fun currentWeather(
        @Query("zip") zipcode: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): Call<CurrentWeather>


}