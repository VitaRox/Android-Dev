package com.example.zipcodebuddy
import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.zipcodebuddy.api.CurrentWeather
import com.example.zipcodebuddy.api.WeeklyForecast
import com.example.zipcodebuddy.api.createOpenWeatherMapService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// Load data, provide data to Activity;
class ForecastRepository {

    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather:  LiveData<CurrentWeather> = _currentWeather

    /*
    Provide data from back to display to user;
    Update via weeklyForecast (public) below;
    Make 'private', since can alter data and pose potential security risk
    otherwise;
    */
    private val _weeklyForecast = MutableLiveData<WeeklyForecast>()
    // weeklyForecast is immutable but can provide data to user view;
    val weeklyForecast: LiveData<WeeklyForecast> = _weeklyForecast


    fun loadWeeklyForecast(zipcode: String) {
        val call = createOpenWeatherMapService().currentWeather(zipcode, "imperial", BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        call.enqueue(object : Callback<CurrentWeather> {
            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName, "error loading location for weekly forecast", t)
            }

            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                val weatherResponse = response.body()
                if (weatherResponse != null) {
                    // Load 7-day forecast;
                    val forecastCall = createOpenWeatherMapService().sevenDayForecast(
                        lat = weatherResponse.coord.lat,
                        lon = weatherResponse.coord.lon,
                        exclude = "currently, minutely, hourly",
                        units = "imperial",
                        apiKey =  BuildConfig.OPEN_WEATHER_MAP_API_KEY
                    )
                    forecastCall.enqueue(object : Callback<WeeklyForecast> {
                        override fun onFailure(call: Call<WeeklyForecast>, t: Throwable) {
                            Log.e(
                                ForecastRepository::class.java.simpleName,
                                "Error loading weekly forecast."
                            )
                        }

                        @SuppressLint("NullSafeMutableLiveData")
                        override fun onResponse(
                            call: Call<WeeklyForecast>,
                            response: Response<WeeklyForecast>
                        ) {
                            val weeklyForecastResponse = response.body()
                            if (weeklyForecastResponse != null) {
                                _weeklyForecast.value = weeklyForecastResponse
                            }
                        }
                    })
                }
            }
        })
    }

    fun loadCurrentForecast(zipcode: String) {
        val call = createOpenWeatherMapService().currentWeather(zipcode, "imperial", BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        call.enqueue(object : Callback<CurrentWeather> {
            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName, "error loading current weather", t)
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                val weatherResponse= response.body()
                if (weatherResponse != null)  {
                    _currentWeather.value = weatherResponse
                }
            }
        })
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

private fun <T> Call<T>.enqueue(callback: Callback<T>, function: () -> Unit) {

}
