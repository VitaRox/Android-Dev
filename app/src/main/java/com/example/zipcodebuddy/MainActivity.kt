package com.example.zipcodebuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    // Create private field to hold reference to our ForecastRepo;
    private val forecastRepository = ForecastRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val zipcodeEditText: EditText = findViewById(R.id.user_inputField)
        val enterButton: Button = findViewById(R.id.enter_button)

        enterButton.setOnClickListener {
            Toast.makeText(this, "Button clicked!", Toast.LENGTH_SHORT).show()

            // Get value of what is entered into text box by user
            // and store in new constant variable, converted to String;
            val zipcode: String = zipcodeEditText.text.toString()

            // Provide user input validation;
            if (zipcode.length != 5) {
//              error case:
                Toast.makeText(this, R.string.user_inputError, Toast.LENGTH_SHORT).show()
            } else {
                forecastRepository.loadForecast(zipcode)
            }
        }

        // Instantiate the container for the displayed forecasts;
        val forecastList: RecyclerView = findViewById(R.id.forecastList)
        forecastList.layoutManager = LinearLayoutManager(this)

        // Handle our click feedback once we get it;
        val dailyForecastAdapter = DailyForecastAdapter() { forecastItem ->
            val msg = getString(R.string.forecast_clicked_format, forecastItem.temp, forecastItem.description)
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        forecastList.adapter = dailyForecastAdapter


        // Notify us when updates occur;
        val weeklyForecastObserver = Observer<List<DailyForecast>> {forecastItems ->
            // Update our recycler view (our list adapter);
            dailyForecastAdapter.submitList(forecastItems)
        }

        // 'This' is our Activity owner;
        // All changes/updates will be bound to the lifecycle of the Activity;
        // If any loading takes too long, won't return after Activity has been
        // destroyed;
        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)
    }
}
