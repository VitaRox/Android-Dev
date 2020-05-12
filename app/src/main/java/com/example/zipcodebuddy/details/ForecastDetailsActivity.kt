package com.example.zipcodebuddy.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.zipcodebuddy.*


class ForecastDetailsActivity : AppCompatActivity() {

    // 'lateinit' is a contract that it will be assigned a value later;
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_details)

        tempDisplaySettingManager = TempDisplaySettingManager(this)
        setTitle("AD 340 Weather")

        // Create references to both Views;
        val tempText = findViewById<TextView>(R.id.tempText)
        val descriptionText = findViewById<TextView>(R.id.descriptionText)

        val temp = intent.getFloatExtra("key_temp", 0f)
        tempText.text = formatTempForDisplay(temp, tempDisplaySettingManager.getTempDisplaySetting())
        descriptionText.text = "${intent.getStringExtra("key_description")}"
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

}
