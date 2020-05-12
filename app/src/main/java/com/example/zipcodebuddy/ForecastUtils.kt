package com.example.zipcodebuddy

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun formatTempForDisplay(temp: Float, tempDisplaySetting: TempDisplaySetting): String {

    // Right click 'when', then do 'Add remaining branches';
    return when (tempDisplaySetting) {
        TempDisplaySetting.Fahrenheit -> String.format("%.2f°", temp)
        TempDisplaySetting.Celsius ->  {
            val temp = (temp - 32f) * (5f/9f)
            String.format("%.2f C°", temp)
        }
    }

}

// Use androidx.app.compat version
fun showTempDisplaySettingDialog(context: Context, tempDisplaySettingManager: TempDisplaySettingManager) {
    // Fluent API: statements can be "chained together" using implicit reference to constructor;
    val dialogBuilder = AlertDialog.Builder(context)
        .setTitle("Choose Display Units")
        .setMessage("Choose which temperature unit to use to display temperature")
        .setPositiveButton("F°") {_, _ ->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Fahrenheit)
        }
        .setNeutralButton("C°") {_, _ ->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Celsius)
        }
        .setOnDismissListener{
            Toast.makeText(context, "Setting will take effect on app restart", Toast.LENGTH_SHORT).show()
        }

    dialogBuilder.show()
}