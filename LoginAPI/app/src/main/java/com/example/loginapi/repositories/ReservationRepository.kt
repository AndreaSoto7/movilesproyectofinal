package com.example.loginapi.repositories

import com.example.loginapi.api.APIProyecto
import com.example.loginapi.models.dto.Reservation
import com.example.loginapi.repositories.RetrofitRepository
import retrofit2.Call
import retrofit2.Response

// ReservationRepository.kt
object ReservationRepository {
    private val apiService: APIProyecto =
        RetrofitRepository.getRetrofitInstance().create(APIProyecto::class.java)

    suspend fun createReservation(token: String, reservation: Reservation): Call<Reservation> {
        return apiService.createReservation(token, reservation)
    }

    suspend fun getReservations(token: String): Response<List<Reservation>> {
        return apiService.getReservations(token)
    }

    suspend fun getReservation(token: String, id: Int): Call<Reservation> {
        return apiService.getReservation(token, id)
    }

    suspend fun getReservationsByRestaurant(
        token: String,
        restaurantId: Int
    ): Response<List<Reservation>> {
        return apiService.getReservationsByRestaurant(token, restaurantId)
    }

    suspend fun cancelReservation(token: String, id: Int): Call<Void> {
        return apiService.cancelReservation(token, id)
    }
}