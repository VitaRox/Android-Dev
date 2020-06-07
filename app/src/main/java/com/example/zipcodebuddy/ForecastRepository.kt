package com.example.zipcodebuddy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*
import kotlin.random.Random


// Load data, provide data to Activity;
class ForecastRepository {

    private val _currentForecast = MutableLiveData<DailyForecast>()
    val currentForecast:  LiveData<DailyForecast> = _currentForecast


    /*
    Provide data from back to display to user;
    Update via weeklyForecast (public) below;
    Make 'private', since can alter data and pose potential security risk
    otherwise;
    */
    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    // weeklyForecast is immutable but can provide data to user view;
    val weeklyForecast: LiveData<List<DailyForecast>> = _weeklyForecast


    fun loadWeeklyForecast(zipcode: String) {
        // Load 10 random values representing forecasts;

        // Create the list;
        val randomValues = List(7){ Random.nextFloat().rem(100) * 100}

        // Populate the list with the data to display to the user;
        val forecastItems = randomValues.map{ temp ->
            DailyForecast(Date(), temp, getTempDescription(temp))
        }
        // Set the value of the private _weeklyForecast; immutable, but destroyed and re-created
        // whenever a new Activity begun;
        _weeklyForecast.setValue(forecastItems)
    }

    fun loadCurrentForecast(zipcode: String) {
        // Produce random val between 0 and 100
        val randomTemp = Random.nextFloat().rem(100) * 100
        val forecast = DailyForecast(Date(), randomTemp, getTempDescription(randomTemp))
        _currentForecast.value = forecast
    }

    // Fetch the written description in accordance to the range the given temp falls in;
    private fun getTempDescription(temp: Float) : String {

        return when(temp) {
            in Float.MIN_VALUE.rangeTo(0f) -> "Anything below 0 doesn't make sense"
            in 0f.rangeTo(32f) -> "Way too coold burrrrr *chatter chatter*"
            in 32f.rangeTo(55f) -> "Balmy Seattle weather, yeaaah"
            else -> "I'm afraid I can't do that, Dave."
        }
    }

}