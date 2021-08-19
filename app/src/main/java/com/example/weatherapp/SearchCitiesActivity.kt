package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.SearchCitiesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchCitiesActivity : AppCompatActivity(), CellClickListener {
    private var cities: MutableList<City> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_cities)

        val cityName = intent.getStringExtra("city_name")

        //request
//        val retrofit = Retrofit.Builder()
//            .baseUrl(baseURL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val service = retrofit.create(WeatherService::class.java)

        val retrofit = ServiceBuilder.buildService(WeatherService::class.java)
        val call = retrofit.getSearchedCities(cityName.toString(), appId)

        call.enqueue(object : Callback<SearchCitiesResponse> {
            override fun onResponse(call: Call<SearchCitiesResponse>, response: Response<SearchCitiesResponse>) {
                if (response.isSuccessful) {
                    val searchCitiesResponse = response.body()!!

                    val searchCities = searchCitiesResponse.list
                    for (item in searchCities) {
                        cities.add(City(item.id, item.name))
                    }
                }
            }
            override fun onFailure(call: Call<SearchCitiesResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "No info for ${cityName.toString()}", Toast.LENGTH_LONG).show()
            }
        })

        val recyclerView = findViewById<RecyclerView>(R.id.searchedCitiesRecyclerView)

        recyclerView.layoutManager = GridLayoutManager(this, 1)//LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val adapter = FoundCitiesAdapter(this, cities as List<City>, this)

        recyclerView.adapter = adapter
    }

    override fun onCellClickListener(cityData: City) {
//        data for selected city
    }

    companion object {

        const val baseURL = "https://api.openweathermap.org/"
        const val appId = "9405140ec7fd6dc8882d7e44f40da75c"
    }

}

interface CellClickListener {
    fun onCellClickListener(cityData: City)
}