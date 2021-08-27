package com.example.weatherapp

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.database.CityData
import com.example.weatherapp.database.CityListViewModel
import com.example.weatherapp.database.CityListViewModelFactory
import com.example.weatherapp.databinding.FragmentFirstBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val cityViewModel: CityListViewModel by activityViewModels{
        CityListViewModelFactory((requireActivity().application as CityApplication).database.cityDao())
    }
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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


        val adapter = CityAdapter {
            cityViewModel.mutableCityInfo.value = it
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(it.name)
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter

        lifecycle.coroutineScope.launch{
            cityViewModel.allCities().collect {
                adapter.submitList(setCityWeatherInfo(it))
            }
        }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu_main, menu)
//
//        val searchItem = menu.findItem(R.id.app_bar_search)
//
//        if (searchItem != null) {
//            val searchView = searchItem.actionView as SearchView
//            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(p0: String?): Boolean {
//                    val intent = Intent(context, SearchCitiesActivity::class.java)
//                    intent.putExtra("city_name", p0)
//                    startActivity(intent)
//
//                    return true
//                }
//
//                override fun onQueryTextChange(p0: String?): Boolean {
//                    return true
//                }
//
//            })
//        }
//
//        return super.onCreateOptionsMenu(menu, inflater)
//    }
}