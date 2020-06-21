package com.example.zipcodebuddy.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.example.zipcodebuddy.TempDisplaySettingManager
import com.example.zipcodebuddy.databinding.FragmentForecastDetailsBinding
import com.example.zipcodebuddy.formatTempForDisplay


class ForecastDetailsFragment : Fragment() {

    private val args: ForecastDetailsFragmentArgs by navArgs()

    private lateinit var viewModelFactory: ForecastDetailsViewModelFactory
    // 'by' keyword indicates a "delegate": defer some piece of functionality
    // to this other class (viewModels);
    // Hands us back a viewmodel that's automatically cached and saved
    // whenever screen rotates or is otherwise updated;
    // We now don't have to recreate this data every time the screen rotates;
    private val viewModel: ForecastDetailsViewModel by viewModels(
        // Can now pass in args to the constructor of the viewModel;
        factoryProducer = { viewModelFactory }
    )

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
        viewModelFactory = ForecastDetailsViewModelFactory(args)
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewStateObserver = Observer<ForecastDetailsViewState> {viewState ->
            // Update the UI
            // Create references to all Views;
            // These create references to the 'root' property of each View element,
            // provided they have a corresponding ID in fragment_forecast_details.xml;
            binding.tempText.text = formatTempForDisplay(viewState.temp, tempDisplaySettingManager.getTempDisplaySetting())
            binding.descriptionText.text = viewState.description
            binding.dateText.text = viewState.date
            binding.forecastIcon.load(viewState.iconUrl)
        }
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Ensures we are clearing the reference to the binding to clean up views on destroy;
        // Helps prevent memory leaks;
        _binding = null
    }
}
