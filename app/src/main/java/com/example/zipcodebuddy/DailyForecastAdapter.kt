package com.example.zipcodebuddy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class DailyForecastViewHolder(view: View)  : RecyclerView.ViewHolder(view) {

    private val tempText = view.findViewById<TextView>(R.id.tempText)
    private val descriptionText: TextView = view.findViewById(R.id.descriptionText)

    fun bind(dailyForecast: DailyForecast) {
        // Format our forecasts to have fewer trailing decimals;
        tempText.text = String.format("%.2f",dailyForecast.temp)
        descriptionText.text = dailyForecast.description
    }
}

class DailyForecastAdapter(
    // Add click handler to our adapter;
    private val clickHandler : (DailyForecast) -> Unit
): ListAdapter<DailyForecast, DailyForecastViewHolder>(DIFF_CONFIG) {


    companion object {
        val DIFF_CONFIG = object : DiffUtil.ItemCallback<DailyForecast>() {
            // Inspect whether oldItem points to the exact same object as newItem;
            override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
                return oldItem === newItem
            }

            // Inspect value of contents;
            override fun areContentsTheSame(
                oldItem: DailyForecast,
                newItem: DailyForecast
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast, parent, false)
        return DailyForecastViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        holder.bind(getItem(position))

        // Q: why did we take this (below) out, or at what point did we do so?  I've watched
        // the entire tutorial > 1 time and took extensive, step-by-step notes but
        // still can't find why I did this or where you said to, and I don't know why
        // we are changing it now to what we changed it to above (
//                .setOnClickListener {it
//            clickHandler(getItem(position))
    }
}


