package com.example.weatherapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.WeatherLayoultBinding
import com.squareup.picasso.Picasso

class WeatherAdapter : ListAdapter<WeatherData, WeatherAdapter.WeatherViewHolder>(WeatherComparator()) {

    class WeatherViewHolder(private val binding: WeatherLayoultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(weather: WeatherData) {
            binding.dayOrHourTextView.text = weather.dayOrHour
            val imageURL = "https://openweathermap.org/img/wn/${weather.icon}@2x.png"
            Picasso.get().load(imageURL).into(binding.iconImageView)
            binding.tempTextView.text = "${weather.maxTemp} °C/ ${weather.minTemp} °C"
        }
    }

    class WeatherComparator : DiffUtil.ItemCallback<WeatherData>() {
        override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem.dayOrHour == newItem.dayOrHour
        }

        override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem.dayOrHour == newItem.dayOrHour
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            WeatherLayoultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}