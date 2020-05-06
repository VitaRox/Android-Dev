package com.example.zipcodebuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    // Create private field to hold reference to our ForecastRepo;
    private val forecastRepository = ForecastRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val zipcode: EditText = findViewById(R.id.user_inputField)
        val enterButton: Button = findViewById(R.id.enter_button)

        enterButton.setOnClickListener {
            Toast.makeText(this, "Button clicked!", Toast.LENGTH_SHORT).show()

            // Get value of what is entered into text box by user
            // and store in new constant variable, converted to String;
            val zipcodeText: String = zipcode.text.toString()

            // Provide user input validation;
            if (zipcodeText.length != 5) {
//              error case:
                Toast.makeText(this, R.string.user_inputError, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, zipcodeText, Toast.LENGTH_SHORT).show()
            }

        }
    }
}
