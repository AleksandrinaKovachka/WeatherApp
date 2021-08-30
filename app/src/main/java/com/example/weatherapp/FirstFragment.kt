package com.example.weatherapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.adapters.CityAdapter
import com.example.weatherapp.data.CityInfo
import com.example.weatherapp.database.CityData
import com.example.weatherapp.database.CityListViewModel
import com.example.weatherapp.database.CityListViewModelFactory
import com.example.weatherapp.databinding.FragmentFirstBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val cityViewModel: CityListViewModel by activityViewModels{
        CityListViewModelFactory((requireActivity().application as CityApplication).database.cityDao())
    }
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        setupAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupAdapter() {
        val adapter = CityAdapter {
            setupAdapterConstructor(it)
        }
        binding.recyclerView.adapter = adapter

        lifecycle.coroutineScope.launch{
            cityViewModel.allCities().collect {
                adapter.submitList(setCityWeatherInfo(it))
            }
        }

        setupItemTouch(adapter)
    }

    private fun setupAdapterConstructor(cityInfo: CityInfo) {
        cityViewModel.mutableCityInfo.value = cityInfo
        val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(cityInfo.name)
        findNavController().navigate(action)
    }

    private suspend fun setCityWeatherInfo(cities: List<CityData>) : List<CityInfo>  {
        val cityInfo: MutableList<CityInfo> = mutableListOf()
        for (city in cities) {
            cityViewModel.getCityWeatherByCityId(city.cityId).collect {
                cityInfo.add(CityInfo(city.cityId, city.cityName, city.countryName, it.main.temp.toInt(), it.weather[0].icon))
            }
        }

        return cityInfo.toList()
    }

    private fun setupItemTouch(adapter: CityAdapter) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val city = adapter.removeItem(viewHolder as CityAdapter.CityViewHolder)
                cityViewModel.delete(city.id, city.name, city.country)
            }

        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }
}