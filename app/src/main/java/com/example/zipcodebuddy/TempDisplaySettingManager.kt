package com.example.zipcodebuddy

import android.content.Context


enum class TempDisplaySetting {
    Fahrenheit, Celsius
}

// Opens up a file on disk to store data to;
class TempDisplaySettingManager(context: Context) {
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun updateSetting(setting: TempDisplaySetting) {
        // Any Kotlin enums have a 'name' property;
        // 'name' is a String mapped to the enum item;
        preferences.edit().putString("key_temp_display", setting.name).commit()
    }

    fun getTempDisplaySetting(): TempDisplaySetting {
        val settingValue = preferences.getString("key_temp_display", TempDisplaySetting.Fahrenheit.name)
        // This maps the name setting to the actual enum type;
        // :? Elvis operator: allows to sub a NULL value in lieu of another value
        return TempDisplaySetting.valueOf(settingValue?:TempDisplaySetting.Fahrenheit.name)
    }
}