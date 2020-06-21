package com.example.zipcodebuddy.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.example.zipcodebuddy.TempDisplaySettingManager
import com.example.zipcodebuddy.databinding.FragmentForecastDetailsBinding
import com.example.zipcodebuddy.formatTempForDisplay
import java.text.SimpleDateFormat
import java.util.*

private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyyy")


class ForecastDetailsFragment : Fragment() {

    private val args: ForecastDetailsFragmentArgs by navArgs()

    private var _binding: FragmentForecastDetailsBinding? = null
    // Property only valid between onCreateView and onDestroyView;
    // !! enforces the type of the binding property to be non-null;
    private val binding get() = _binding!!

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastDetailsBinding.inflate(inflater, container, false)

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        // Create references to all Views;
        // These create references to the 'root' property of each View element,
        // provided they have a corresponding ID in fragment_forecast_details.xml;
        binding.tempText.text = formatTempForDisplay(args.temp, tempDisplaySettingManager.getTempDisplaySetting())
        binding.descriptionText.text = args.description
        binding.dateText.text = DATE_FORMAT.format(Date(args.date * 1000))
        binding.forecastIcon.load("http://openweathermap.org/img/wn/${args.icon}@2x.png")

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Ensures we are clearing the reference to the binding to clean up views on destroy;
        // Helps prevent memory leaks;
        _binding = null
    }
}
