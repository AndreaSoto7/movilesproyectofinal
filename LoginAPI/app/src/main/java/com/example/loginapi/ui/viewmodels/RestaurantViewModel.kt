package com.example.loginapi.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapi.api.APIProyecto
import com.example.loginapi.models.dto.Restaurant
import com.example.loginapi.repositories.RetrofitRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantViewModel : ViewModel() {
    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> get() = _restaurants

    fun fetchRestaurants(searchParams: Map<String, String>) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(APIProyecto::class.java)
        service.searchRestaurants(searchParams)
            .enqueue(object : Callback<List<Restaurant>> {
                override fun onResponse(
                    call: Call<List<Restaurant>>,
                    response: Response<List<Restaurant>>
                ) {
                    if (response.isSuccessful) {
                        _restaurants.value = response.body()
                        Log.d("RestaurantViewModel", "Restaurants received: ${response.body()}")
                    } else {
                        Log.e("RestaurantViewModel", "Error fetching restaurants: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                    Log.e("RestaurantViewModel", "Error fetching restaurants", t)
                }
            })
    }
}