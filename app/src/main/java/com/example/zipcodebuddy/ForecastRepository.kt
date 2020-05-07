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
        // Load 10 random values representing forecasts;

        // Create the list;
        val randomValues = List(10){ Random.nextFloat().rem(100) * 100}

        // Populate the list with the data to display to the user;
        val forecastItems = randomValues.map{ temp ->
            DailyForecast(temp, getTempDescription(temp))
        }
        // Set the value of the private _weeklyForecast; immutable, but destroyed and re-created
        // whenever a new Activity begun;
        _weeklyForecast.setValue(forecastItems)
    }

    // Fetch the written description in accordance to the range the given temp falls in;
    private fun getTempDescription(temp: Float) : String {
//        return if(temp < 75) "It's too dang cold!" else "It's hot, but it's a DRY heat."

        // OR
        return when(temp) {
            in Float.MIN_VALUE.rangeTo(0f) -> "Anything below 0 doesn't make sense."
            in 0f.rangeTo(32f) -> "Way toooooooo cooooooold burrrrr *chatter chatter*"
            in 32f.rangeTo(55f) -> "Balmy Seattle weather, yeaaahhhh.."
            else -> "I'm afraid I can't do that, Dave."
        }
    }

}