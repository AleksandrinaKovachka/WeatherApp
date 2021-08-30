package com.example.weatherapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapters.FoundCitiesAdapter
import com.example.weatherapp.data.CityInfo
import com.example.weatherapp.database.CityListViewModel
import com.example.weatherapp.database.CityListViewModelFactory
import com.example.weatherapp.databinding.ActivitySearchCitiesBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.Serializable

class SearchCitiesActivity : AppCompatActivity() {

    private val searchViewModel: CityListViewModel by viewModels {
        CityListViewModelFactory((application as CityApplication).database.cityDao())
    }

    private lateinit var adapter: FoundCitiesAdapter
    private lateinit var binding: ActivitySearchCitiesBinding
    private lateinit var cityName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchCitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cityName = intent.getStringExtra("city_name").toString()

        binding.searchedCitiesRecyclerView.layoutManager = LinearLayoutManager(baseContext)

        setupAdapter()
    }

    private fun setupAdapter() {
        adapter = FoundCitiesAdapter{
            setupAdapterConstructor(it)
        }
        binding.searchedCitiesRecyclerView.adapter = adapter

        lifecycleScope.launch {
            searchCities()
        }
    }

    private fun setupAdapterConstructor(cityInfo: CityInfo) {
        searchViewModel.insert(cityInfo.id, cityInfo.name, cityInfo.country)
        intent.putExtra("city_info", cityInfo as Serializable)
        setResult(RESULT_OK, intent)
        finish()
    }

    private suspend fun searchCities() {
        cityName.apply {
            searchViewModel.getCitiesByName(cityName).collect {
                val citiesInfo = it.list.map { city ->
                    CityInfo(city.id, city.name, city.sys.country, city.main.temp.toInt(), city.weather[0].icon)
                }.toList()
                adapter.submitList(citiesInfo)
            }
        }
    }
}
