package com.example.zipcodebuddy.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory.create
import retrofit2.http.GET
import retrofit2.http.Query

// function to return a valid imp of the interface;
fun createOpenWeatherMapService() : OpenWeatherMapService {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://api.openweathermap.org")
        .addConverterFactory(create())
        .build()

    return retrofit.create(OpenWeatherMapService::class.java)
}
/**
 * http://api.openweathermap.org/data/2.5/weather?zip=98119&units=imperial&appid=<apikey>
 */
// Serve as basis of our Retrofit API service;
interface OpenWeatherMapService {

    @GET("/data/2.5/weather")
    fun currentWeather(
        @Query("zip") zipcode: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Call<CurrentWeather>

    /**
     * https://api.openweathermap.org/data/2.5/onecall?lat=47.64&lon=-122.36&exclude=current,minutely,hourly&units=imperial&appid=<apikey>
     */
    @GET("/data/2.5/onecall")
    fun sevenDayForecast(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("exclude") exclude: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): Call<WeeklyForecast>
}