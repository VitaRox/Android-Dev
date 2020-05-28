package com.example.zipcodebuddy

interface AppNavigator {

    fun navigateToCurrentForecast(zipcode: String)
    fun navigateToLocationEntry()
    fun navigateToForecastDetails(forecast: DailyForecast)
}