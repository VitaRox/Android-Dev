package com.example.zipcodebuddy.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.zipcodebuddy.*
import com.example.zipcodebuddy.api.CurrentWeather
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * A simple [Fragment] subclass.
 */
class CurrentForecastFragment : Fragment() {

    // Create private field to hold reference to our ForecastRepo;
    private val forecastRepository = ForecastRepository()
    private lateinit var locationRepository: LocationRepository
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)
        val locationName: TextView = view.findViewById(R.id.locationName)
        val tempText: TextView = view.findViewById(R.id.tempText)
        // Arguments gives access to Bundle passed into this instance;
        // !! will crash it if a null value is passed in for KEY_ZIPCODE;
        // '?:' The Elvis operator; will return an empty string if null value passed in;
        val zipcode = arguments?.getString(KEY_ZIPCODE) ?: ""
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        // Create the observer which updates the UI in response to forecast updates;
        val currentWeatherObserver = Observer<CurrentWeather> { weather ->
            locationName.text = weather.name
            tempText.text = formatTempForDisplay(weather.forecast.temp, tempDisplaySettingManager.getTempDisplaySetting())
        }
        // All changes/updates will be bound to the lifecycle of the Activity;
        // If any loading takes too long, won't return after Activity has been
        // destroyed;
        forecastRepository.currentWeather.observe(viewLifecycleOwner, currentWeatherObserver)

        // Connect button and add click listener;
        val locationEntryButton: FloatingActionButton = view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
            showLocationEntry()
        }

        // Listen for changes in this savedLocation value;
        // Refresh UI upon update to value of savedLocation;
        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location> {savedLocation ->
            when (savedLocation) {
                is Location.Zipcode -> forecastRepository.loadCurrentForecast(savedLocation.zipcode)
            }
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner,  savedLocationObserver)

        return view
    }

    private fun showLocationEntry() {
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }

    // Puts forecast info into the intent, then starts the Activity;
    private fun showForecastDetails(forecast: DailyForecast) {
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToForecastDetailsFragment(forecast.temp, forecast.description)
        findNavController().navigate(action)
    }

    // Object scoped to an instance of currentForecastFragment, in this case;
    // Can replicate the behavior of static methods in Java;
    companion object {
        const val KEY_ZIPCODE = "key_zipcode"

        fun newInstance(zipcode: String) : CurrentForecastFragment {
            val fragment = CurrentForecastFragment()

            // Bundle: simple class to pass on key-value pairs
            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipcode)
            fragment.arguments = args

            return fragment
        }
    }

}
