package com.example.weatherapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.weatherapp.data.CityInfo
import com.example.weatherapp.database.CityListViewModel
import com.example.weatherapp.database.CityListViewModelFactory
import com.example.weatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val searchViewModel: CityListViewModel by viewModels {
        CityListViewModelFactory((application as CityApplication).database.cityDao())
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        setupResultLauncher(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchItem.apply {
            setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    val intent = Intent(context, SearchCitiesActivity::class.java)
                    intent.putExtra("city_name", p0)

                    clearFocus()
                    setQuery("", false)
                    isIconified = true
                    resultLauncher.launch(intent)

                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }
            })
        }

        return true
    }

    private fun setupResultLauncher(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val cityInfo = result.data?.getSerializableExtra("city_info") as CityInfo
            searchViewModel.mutableCityInfo.value = cityInfo
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(cityInfo.name)
            findNavController(R.id.nav_host_fragment_content_main).navigate(action)
        }
    }
}