package com.example.zipcodebuddy.details

data class ForecastDetailsViewState (
    // Represents data we ultimately want to display;
    val temp: Float,
    val description: String,
    val date: String,
    val iconUrl: String
)