package com.example.weatherapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.database.CityListViewModel
import com.example.weatherapp.database.CityListViewModelFactory
import com.example.weatherapp.databinding.FragmentSecondBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val cityWeatherViewModel: CityListViewModel by activityViewModels{
        CityListViewModelFactory((requireActivity().application as CityApplication).database.cityDao())
    }
    private var cityId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityInfo = cityWeatherViewModel.mutableCityInfo.value
        if (cityInfo != null) {
            cityId = cityInfo.id
            binding.tempTextView.text = "${cityInfo.temp.toString()} °C"
            val imageURL = "https://openweathermap.org/img/wn/${cityInfo.img}@2x.png"
            Picasso.get().load(imageURL).into(binding.tempIconImageView)
        }

        binding.hourlyRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.weeklyRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val hourlyAdapter = WeatherAdapter()
        val weeklyAdapter = WeatherAdapter()

        binding.hourlyRecyclerView.adapter = hourlyAdapter
        binding.weeklyRecyclerView.adapter = weeklyAdapter

        lifecycle.coroutineScope.launch {
            cityWeatherViewModel.getWeatherForecastByCityId(cityId).collect {
                binding.humidityTextView.text = "Humidity: ${it.list[0].main.humidity.toString()}"
                binding.pressureTextView.text = "Pressure: ${it.list[0].main.pressure.toString()}"
                binding.windSpeedTextView.text = "Wind speed: ${it.list[0].wind.speed.toString()}"

                val weatherData = it.list.map { forecast ->
                    WeatherData(forecast.dt_txt, forecast.weather[0].icon, forecast.main.temp_max.toInt(), forecast.main.temp_min.toInt())
                }.toList()

                hourAndDateForecast(weatherData, hourlyAdapter, weeklyAdapter)
            }
        }


    }

    private fun hourAndDateForecast(weatherData: List<WeatherData>, hourlyAdapter: WeatherAdapter, weeklyAdapter: WeatherAdapter) {
        val currentDay = weatherData[0].dayOrHour.subSequence(8, 10)
        val hourlyData = weatherData.slice(IntRange(0, 4))
        var hour: String

        for (item in hourlyData) {
            hour = item.dayOrHour.substring(11)
            hour = hour.dropLast(3)
            item.dayOrHour = hour
        }
        hourlyAdapter.submitList(hourlyData)

        val weather = weatherData.slice(IntRange(5, weatherData.size - 1))
        val weeklyList: MutableList<WeatherData> = mutableListOf()

        for (item in weather) {
            hour = item.dayOrHour.subSequence(11, 13) as String
            if (hour.toInt() == 12 && item.dayOrHour.length > 6 && item.dayOrHour.subSequence(8, 10) != currentDay) {
                weeklyList.add(WeatherData(item.dayOrHour.subSequence(0, 10) as String, item.icon, item.maxTemp, item.minTemp))
            }
        }
        weeklyAdapter.submitList(weeklyList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}