package com.example.weatherapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoundCitiesAdapter(private val context: Context, private val foundCitiesList: List<City>, private val cellClickListener: SearchCitiesActivity) : RecyclerView.Adapter<FoundCitiesAdapter.FoundCitiesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoundCitiesViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.search_cities_layout, parent, false)
        return FoundCitiesViewHolder(v)
    }

    override fun onBindViewHolder(holder: FoundCitiesViewHolder, position: Int) {
        val city = foundCitiesList[position]
        holder.cityNameTextView.text = city.name

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(city)
        }
    }

    override fun getItemCount(): Int {
        return foundCitiesList.size
    }

    class FoundCitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cityNameTextView : TextView = itemView.findViewById(R.id.foundCityTextView)
    }
}