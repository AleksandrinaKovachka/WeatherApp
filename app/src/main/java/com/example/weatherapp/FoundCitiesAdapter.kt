package com.example.weatherapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.SearchCitiesLayoutBinding

class FoundCitiesAdapter(private val onItemClick: (City) -> Unit) :
    ListAdapter<City, FoundCitiesAdapter.FoundCitiesViewHolder>(CitiesComparator()) {

    class FoundCitiesViewHolder(private val binding: SearchCitiesLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(city: City) {
                binding.countryTextView.text = city.country
                binding.foundCityTextView.text = city.name
            }
    }

    class CitiesComparator : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoundCitiesViewHolder {
        return FoundCitiesViewHolder(
            SearchCitiesLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FoundCitiesViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            onItemClick(getItem(position))
        }
    }
}

