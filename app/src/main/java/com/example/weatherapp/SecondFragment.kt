package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapters.WeatherAdapter
import com.example.weatherapp.data.WeatherData
import com.example.weatherapp.database.CityListViewModel
import com.example.weatherapp.database.CityListViewModelFactory
import com.example.weatherapp.databinding.FragmentSecondBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val cityWeatherViewModel: CityListViewModel by activityViewModels{
        CityListViewModelFactory((requireActivity().application as CityApplication).database.cityDao())
    }
    private var cityId: Int = 0
    private val hourlyAdapter = WeatherAdapter()
    private val weeklyAdapter = WeatherAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTempData()
        setupAdapters()
    }

        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    private fun setupTempData() {
        val cityInfo = cityWeatherViewModel.mutableCityInfo.value
        if (cityInfo != null) {
            cityId = cityInfo.id
            binding.tempTextView.text = "${cityInfo.temp} °C"
            val imageURL = "https://openweathermap.org/img/wn/${cityInfo.img}@2x.png"
            Picasso.get().load(imageURL).into(binding.tempIconImageView)
        }
    }

    private fun setupAdapters() {
        binding.hourlyRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.weeklyRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.hourlyRecyclerView.adapter = hourlyAdapter
        binding.weeklyRecyclerView.adapter = weeklyAdapter

        lifecycle.coroutineScope.launch {
            setupForecastData()
        }
    }

    @SuppressLint("SetTextI18n")
    private suspend fun setupForecastData() {
        cityWeatherViewModel.getWeatherForecastByCityId(cityId).collect {
            binding.humidityTextView.text = "Humidity: ${it.list[0].main.humidity}"
            binding.pressureTextView.text = "Pressure: ${it.list[0].main.pressure}"
            binding.windSpeedTextView.text = "Wind speed: ${it.list[0].wind.speed}"

            val weatherData = it.list.map { forecast ->
                WeatherData(forecast.dt_txt, forecast.weather[0].icon, forecast.main.temp_max.toInt(), forecast.main.temp_min.toInt())
            }.toList()

            hourAndDateForecast(weatherData)
        }
    }

    private fun hourAndDateForecast(weatherData: List<WeatherData>) {
        val currentDay = weatherData[0].dayOrHour.subSequence(8, 10)
        val hourlyData = weatherData.slice(IntRange(0, 4))
        hourForecast(hourlyData)

        val weather = weatherData.slice(IntRange(5, weatherData.size - 1))
        weeklyForecast(weather, currentDay as String)
    }

    private fun hourForecast(hourlyData: List<WeatherData>) {
        var hour: String

        for (item in hourlyData) {
            hour = item.dayOrHour.subSequence(11, 16) as String
            item.dayOrHour = hour
        }
        hourlyAdapter.submitList(hourlyData)
    }

    private fun weeklyForecast(weather: List<WeatherData>, currentDay: String) {
        val weeklyList: MutableList<WeatherData> = mutableListOf()
        var hour: String

        for (item in weather) {
            hour = item.dayOrHour.subSequence(11, 13) as String
            if (hour.toInt() == 12 && item.dayOrHour.length > 6 && item.dayOrHour.subSequence(8, 10) != currentDay) {
                weeklyList.add(WeatherData(item.dayOrHour.subSequence(0, 10) as String, item.icon, item.maxTemp, item.minTemp))
            }
        }
        weeklyAdapter.submitList(weeklyList)
    }
}