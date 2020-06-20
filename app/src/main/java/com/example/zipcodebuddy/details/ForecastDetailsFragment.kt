package com.example.zipcodebuddy.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.example.zipcodebuddy.R
import com.example.zipcodebuddy.TempDisplaySettingManager
import com.example.zipcodebuddy.formatTempForDisplay
import java.text.SimpleDateFormat
import java.util.*

private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyyy")


class ForecastDetailsFragment : Fragment() {

    private val args: ForecastDetailsFragmentArgs by navArgs()

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_forecast_details, container, false)

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        // Create references to both Views;
        val tempText = layout.findViewById<TextView>(R.id.tempText)
        val descriptionText = layout.findViewById<TextView>(R.id.descriptionText)
        val dateText: TextView = layout.findViewById<TextView>(R.id.dateText)
        val forecastIcon: ImageView = layout.findViewById<ImageView>(R.id.forecastIcon)
        tempText.text = formatTempForDisplay(args.temp, tempDisplaySettingManager.getTempDisplaySetting())
        descriptionText.text = args.description
        dateText.text = DATE_FORMAT.format(Date(args.date * 1000))
        forecastIcon.load("http://openweathermap.org/img/wn/${args.icon}@2x.png")

        return layout
    }
}
