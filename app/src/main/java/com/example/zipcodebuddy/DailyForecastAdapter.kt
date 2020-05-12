package com.example.zipcodebuddy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class DailyForecastViewHolder(
    view: View,
    private val tempDisplaySettingManager: TempDisplaySettingManager
    )  : RecyclerView.ViewHolder(view) {

    private val tempText = view.findViewById<TextView>(R.id.tempText)
    private val descriptionText: TextView = view.findViewById(R.id.descriptionText)

    fun bind(dailyForecast: DailyForecast) {
        // Format our forecasts to have fewer trailing decimals;
        tempText.text = formatTempForDisplay(dailyForecast.temp, tempDisplaySettingManager.getTempDisplaySetting())
        descriptionText.text = dailyForecast.description
    }
}

class DailyForecastAdapter(
    private val tempDisplaySettingManager: TempDisplaySettingManager,
    // Add click handler to our adapter;
    private val clickHandler : (DailyForecast) -> Unit
): ListAdapter<DailyForecast, DailyForecastViewHolder>(DIFF_CONFIG) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast, parent, false)
        return DailyForecastViewHolder(itemView, tempDisplaySettingManager)
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        holder.bind(getItem(position))


        holder.itemView.setOnClickListener {
            clickHandler(getItem(position))
        }
    }

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


}


