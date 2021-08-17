package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.database.CityData


class CityAdapter(private val onItemClick: (String) -> Unit) : ListAdapter<CityData, CityAdapter.CityViewHolder>(CitiesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {

        val current = getItem(position)

        holder.cityNameTextView.text = current.cityName
        //generate data

        holder.itemView.setOnClickListener {
            onItemClick(current.cityId)
        }
    }

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityNameTextView: TextView = itemView.findViewById(R.id.cityNameTextView)
        val degreeTextView: TextView = itemView.findViewById(R.id.degreeTextView)
        val weatherIconImageView: ImageView = itemView.findViewById(R.id.weatherIconImageView)

        companion object {
            fun create(parent: ViewGroup): CityViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.city_layout, parent, false)
                return CityViewHolder(view)
            }
        }
    }

    class CitiesComparator : DiffUtil.ItemCallback<CityData>() {
        override fun areItemsTheSame(oldItem: CityData, newItem: CityData): Boolean {
            return oldItem.cityId == newItem.cityId
        }

        override fun areContentsTheSame(oldItem: CityData, newItem: CityData): Boolean {
            return oldItem.cityName == newItem.cityName
        }
    }
}