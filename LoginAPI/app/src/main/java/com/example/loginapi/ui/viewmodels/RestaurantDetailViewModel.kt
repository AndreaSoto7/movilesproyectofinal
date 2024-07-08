package com.example.loginapi.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginapi.api.APIProyecto
import com.example.loginapi.models.dto.Restaurant
import com.example.loginapi.repositories.RestaurantRepository
import com.example.loginapi.repositories.RetrofitRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeoutException

class RestaurantDetailViewModel : ViewModel() {

    private val _restaurantDetails = MutableLiveData<Restaurant>()
    val restaurantDetails: LiveData<Restaurant> get() = _restaurantDetails

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val restaurantApi: APIProyecto = RetrofitRepository.getRetrofitInstance().create(APIProyecto::class.java)

    fun insertRestaurant(token: String, name: String, address: String, city: String, description: String) {
        viewModelScope.launch {
            try {
                val restaurantData = mapOf(
                    "name" to name,
                    "address" to address,
                    "city" to city,
                    "description" to description
                )

                val response = RetrofitRepository.getRetrofitInstance().create(APIProyecto::class.java)
                    .insertRestaurant("Bearer $token", restaurantData)

                if (response.isSuccessful) {
                    _errorMessage.value = "" // Limpia el mensaje de error
                } else {
                    _errorMessage.value = "Error al insertar el restaurante: ${response.code()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error al insertar el restaurante: ${e.message}"
            }
        }
    }

    fun getRestaurantDetails(restaurantId: Int) {
        viewModelScope.launch {
            try {
                val response = RestaurantRepository.getRestaurantDetails(restaurantId)
                if (response.isSuccessful) {
                    _restaurantDetails.value = response.body()
                } else {
                    _errorMessage.value = "Failed to load restaurant details"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun updateRestaurant(
        restaurantId: Int,
        token: String,
        newName: String,
        newAddress: String,
        newCity: String,
        newDescription: String
    ) {
        viewModelScope.launch {
            try {
                val response = RestaurantRepository.updateRestaurant(
                    restaurantId,
                    token,
                    newName,
                    newAddress,
                    newCity,
                    newDescription
                )
                if (!response.isSuccessful) {
                    _errorMessage.value = "Failed to update restaurant"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error updating restaurant: ${e.message}"
            }
        }
    }

    fun deleteRestaurant(restaurantId: Int, token: String) {
        viewModelScope.launch {
            Log.d("RestaurantDetailViewModel", "Starting deleteRestaurant coroutine")
            try {
                withTimeout(5000) {
                    Log.d("RestaurantDetailViewModel", "Sending DELETE request for restaurant $restaurantId")
                    val response = restaurantApi.deleteRestaurant("Bearer $token", restaurantId)
                    if (response.isSuccessful) {
                        Log.d("RestaurantDetailViewModel", "Restaurant deleted successfully (code ${response.code()})")
                        _errorMessage.value = "" // Clear any previous error message
                    } else {
                        Log.e("RestaurantDetailViewModel", "DELETE request failed with code: ${response.code()}, message: ${response.message()}")
                        _errorMessage.value = "Error deleting restaurant: ${response.code()}"
                    }
                }
            } catch (e: TimeoutException) {
                Log.e("RestaurantDetailViewModel", "Timeout deleting restaurant: ${e.message}")
                _errorMessage.value = "Timeout deleting restaurant"
            } catch (e: Exception) {
                Log.e("RestaurantDetailViewModel", "Error deleting restaurant: ${e.message}")
                _errorMessage.value = "Error deleting restaurant: ${e.message}"
            }
        }
    }
}