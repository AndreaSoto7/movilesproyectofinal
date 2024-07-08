package com.example.loginapi.repositories

import com.example.loginapi.api.APIProyecto
import com.example.loginapi.models.dto.Restaurant
import retrofit2.Call
import retrofit2.Response

object RestaurantRepository {

    private val apiService: APIProyecto by lazy {
        RetrofitRepository.getRetrofitInstance().create(APIProyecto::class.java)
    }

    suspend fun getRestaurantDetails(restaurantId: Int): Response<Restaurant> {
        return apiService.getRestaurantDetails(restaurantId)
    }

    suspend fun updateRestaurant(restaurantId: Int, token: String, newName: String, newAdress: String, newCity: String, newDescription: String): Response<Void> {
        val restaurantUpdateRequest = Restaurant.RestaurantUpdateRequest(
            newName,
            newAdress,
            newCity,
            newDescription
        )
        return apiService.updateRestaurant(restaurantId, "Bearer $token", restaurantUpdateRequest)
    }
}
