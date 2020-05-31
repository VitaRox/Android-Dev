package com.example.zipcodebuddy.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.zipcodebuddy.R
/**
 * A simple [Fragment] subclass.
 */
class LocationEntryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location_entry, container, false)

        // Update UI, get view references, etc.
        val zipcodeEditText: EditText = view.findViewById(R.id.zipcodeEditText)
        val enterButton: Button = view.findViewById(R.id.enter_button)

        enterButton.setOnClickListener {

            // Get value of what is entered into text box by user
            // and store in new constant variable, converted to String;
            val zipcode: String = zipcodeEditText.text.toString()

            // Provide user input validation;
            if (zipcode.length != 5) {
//              error case:
                Toast.makeText(requireContext(), R.string.user_inputError, Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigateUp()
            }
        }

        return view
    }

}
