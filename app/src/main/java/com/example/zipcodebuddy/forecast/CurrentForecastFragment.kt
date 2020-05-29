package com.example.zipcodebuddy.forecast

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zipcodebuddy.*
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * A simple [Fragment] subclass.
 */
class CurrentForecastFragment : Fragment() {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    // Create private field to hold reference to our ForecastRepo;
    private val forecastRepository = ForecastRepository()

    private lateinit var appNavigator: AppNavigator

    // This is where the Fragment is added to the Activity;
    // Provides access to the Activity, or context;
    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = context as AppNavigator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        // Arguments gives access to Bundle passed into this instance;
        // !! will crash it if a null value is passed in for KEY_ZIPCODE;
        // '?:' The Elvis operator; will return an empty string if null value passed in;
        val zipcode = arguments!!.getString(KEY_ZIPCODE) ?: ""

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)

        // Connect button and add click listener;
        val locationEntryButton: FloatingActionButton = view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
            appNavigator.navigateToLocationEntry()
        }
        // Instantiate the container for the displayed forecasts;
        val forecastList: RecyclerView = view.findViewById(R.id.forecastList)
        forecastList.layoutManager = LinearLayoutManager(requireContext())

        // Handle our click feedback once we get it;
        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager) { forecast ->
            showForecastDetails(forecast)
        }

        forecastList.adapter = dailyForecastAdapter

        // Notify us when updates occur;
        val weeklyForecastObserver = Observer<List<DailyForecast>> { forecastItems ->
            // Update our recycler view (our list adapter);
            dailyForecastAdapter.submitList(forecastItems)
        }

        // 'This' is our Activity owner;
        // All changes/updates will be bound to the lifecycle of the Activity;
        // If any loading takes too long, won't return after Activity has been
        // destroyed;
        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)

        forecastRepository.loadForecast(zipcode)
        return view
    }

    // Puts forecast info into the intent, then starts the Activity;
    private fun showForecastDetails(forecast: DailyForecast) {
        appNavigator.navigateToForecastDetails(forecast)
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
