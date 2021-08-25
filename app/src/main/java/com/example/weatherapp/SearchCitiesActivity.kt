package com.example.weatherapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.database.CityListViewModel
import com.example.weatherapp.database.CityListViewModelFactory
import com.example.weatherapp.databinding.ActivitySearchCitiesBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchCitiesActivity : AppCompatActivity() {

    private val searchViewModel: CityListViewModel by viewModels {
        CityListViewModelFactory((application as CityApplication).database.cityDao())
    }

    private lateinit var adapter: FoundCitiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySearchCitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cityName = intent.getStringExtra("city_name")

        binding.searchedCitiesRecyclerView.layoutManager = LinearLayoutManager(baseContext)

        adapter = FoundCitiesAdapter{
            searchViewModel.insert(it.id, it.name, it.country)
            finish()
        }
        binding.searchedCitiesRecyclerView.adapter = adapter

        lifecycleScope.launch {
            cityName?.apply {
                searchViewModel.getCitiesByName(cityName).collect {
                    val citiesInfo = it.list.map { city ->
                        City(city.id, city.name, city.sys.country)
                    }.toList()
                    adapter.submitList(citiesInfo)
                }
            }
        }


    }
}
