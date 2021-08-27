package com.example.weatherapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.database.CityData
import com.squareup.picasso.Picasso


class CityAdapter(private val onItemClick: (CityInfo) -> Unit) : ListAdapter<CityInfo, CityAdapter.CityViewHolder>(CitiesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder.create(parent)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {

        val current = getItem(position)

        holder.cityNameTextView.text = current.name
        holder.countryTextView.text = current.country
        holder.degreeTextView.text = "${current.temp} °C"

        val imageURL = "https://openweathermap.org/img/wn/${current.img}@2x.png"
        Picasso.get().load(imageURL).into(holder.weatherIconImageView)

        holder.itemView.setOnClickListener {
            onItemClick(current)
        }
    }

    fun removeItem(viewHolder: CityViewHolder) : CityInfo {
        return getItem(viewHolder.adapterPosition)
    }

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityNameTextView: TextView = itemView.findViewById(R.id.cityNameTextView)
        val countryTextView: TextView = itemView.findViewById(R.id.countryTextView)
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

    class CitiesComparator : DiffUtil.ItemCallback<CityInfo>() {
        override fun areItemsTheSame(oldItem: CityInfo, newItem: CityInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CityInfo, newItem: CityInfo): Boolean {
            return oldItem.name == newItem.name
        }
    }
}