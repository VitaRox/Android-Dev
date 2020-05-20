package com.example.zipcodebuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zipcodebuddy.details.ForecastDetailsActivity
import com.example.zipcodebuddy.location.LocationEntryFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    // Create private field to hold reference to our ForecastRepo;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempDisplaySettingManager = TempDisplaySettingManager(this)

        val zipcodeEditText: EditText = findViewById(R.id.zipcodeEditText)
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
        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager) { forecast ->
            showForecastDetails(forecast)
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

        // Tells where to add Fragment
        supportFragmentManager
            .beginTransaction()
            .add(R.id.root, LocationEntryFragment())
//            .addToBackStack()
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection, check id and respond accordingly;
        return when (item.itemId) {
            R.id.tempDisplaySetting -> {
//               Toast.makeText(this, "clicked menu item", Toast.LENGTH_SHORT).show()
                showTempDisplaySettingDialog(this, tempDisplaySettingManager)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Use androidx.app.compat version
    private fun showTempDisplaySettingDialog() {
        // Fluent API: statements can be "chained together" using implicit reference to constructor;
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Choose Display Units")
            .setMessage("Choose which temperature unit to use to display temperature")
            .setPositiveButton("F°") {_, _ ->
                tempDisplaySettingManager.updateSetting(TempDisplaySetting.Fahrenheit)
            }
            .setNeutralButton("C°") {_, _ ->
                tempDisplaySettingManager.updateSetting(TempDisplaySetting.Celsius)
            }
            .setOnDismissListener{
                Toast.makeText(this, "Setting will take effect on app restart", Toast.LENGTH_SHORT).show()
            }

        dialogBuilder.show()
    }




    // Puts forecast info into the intent, then starts the Activity;
    private fun showForecastDetails(forecast: DailyForecast) {
        val forecastDetailsIntent = Intent(this, ForecastDetailsActivity::class.java)
        forecastDetailsIntent.putExtra("key_temp", forecast.temp)
        forecastDetailsIntent.putExtra("key_description", forecast.description)
        startActivity(forecastDetailsIntent)
    }
}
