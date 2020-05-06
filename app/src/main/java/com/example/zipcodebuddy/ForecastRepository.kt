package com.example.zipcodebuddy
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import kotlin.random.Random


// Load data, provide data to Activity;
class ForecastRepository {

    /*
    Provide data from back to display to user;
    Update via weeklyForecast (public) below;
    Make 'private', since can alter data and pose potential security risk
    otherwise;
    */
    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()

    // weeklyForecast is immutable but can provide data to user view;
    val weeklyForecast: LiveData<List<DailyForecast>> = _weeklyForecast


    fun loadForecast(zipcode: String) {
        // Load 7 random values representing forecasts;
        val randomValues = List(7){ Random.nextFloat().rem(100) * 100}

        val forecastItems = randomValues.map{temp ->
            DailyForecast(temp, "Partly cloudy")
        }
        _weeklyForecast.setValue(forecastItems)

    }



}