package com.example.loginapi.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginapi.api.APIProyecto
import com.example.loginapi.models.dto.MenuCategory
import com.example.loginapi.models.dto.Reservation
import com.example.loginapi.models.dto.Restaurant
import com.example.loginapi.repositories.RetrofitRepository
import kotlinx.coroutines.launch

class ReservationViewModel : ViewModel() {
    private val _restaurantDetails = MutableLiveData<Restaurant>()
    val restaurantDetails: LiveData<Restaurant> = _restaurantDetails
    private val _reservationDetails = MutableLiveData<Reservation>()
    val reservationDetails: LiveData<Reservation> = _reservationDetails
    private val _menu = MutableLiveData<List<MenuCategory>>()
    val menu: LiveData<List<MenuCategory>> = _menu


    private val _reservations = MutableLiveData<List<Reservation>>() // Declaración de _reservations
    val reservations: LiveData<List<Reservation>> = _reservations

    fun getReservations(token: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitRepository.getRetrofitInstance().create(APIProyecto::class.java).getReservations(token)
                if (response.isSuccessful) {
                    _reservations.value = response.body()
                } else {
                    Log.e("ReservationViewModel", "Error al obtener las reservas: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ReservationViewModel", "Error al obtener las reservas", e)
            }
        }
    }

    fun createReservation(token: String, reservation: Reservation) {
        viewModelScope.launch {
            try {
                val call = RetrofitRepository.getRetrofitInstance().create(APIProyecto::class.java)
                    .createReservation(token, reservation)
                val response = call.execute() // Ejecuta la llamada y espera la respuesta
                if (response.isSuccessful) {
                    Log.d("ReservationViewModel", "Reserva creada con éxito")
                } else {
                    Log.e("ReservationViewModel", "Error al crear la reserva: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ReservationViewModel", "Error al crear la reserva", e)
            }
        }
    }

    fun fetchRestaurantDetails(restaurantId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitRepository.getRetrofitInstance().create(APIProyecto::class.java).getRestaurantDetails(restaurantId)
                if (response.isSuccessful) {
                    _restaurantDetails.value = response.body()
                    fetchMenu(restaurantId)
                } else {
                    Log.e("ReservationViewModel", "Error al obtener los detalles del restaurante: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ReservationViewModel", "Error al obtener los detalles del restaurante", e)
            }
        }
    }

    fun fetchReservationDetails(restaurantId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitRepository.getRetrofitInstance().create(APIProyecto::class.java)
                    .getReservationDetails(restaurantId)
                if (response.isSuccessful) {
                    val reservations = response.body()
                    Log.d("ReservationViewModel", "Respuesta del API para reservas: $reservations")
                    if (!reservations.isNullOrEmpty()) {
                        _reservationDetails.value = reservations[0] // Asumiendo que la respuesta es una lista con una sola reserva
                    } else {
                        Log.e("ReservationViewModel", "Error: La lista de reservas está vacía o es nula.")
                    }
                } else {
                    Log.e("ReservationViewModel", "Error al obtener los detalles de la reserva: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ReservationViewModel", "Error al obtener los detalles de la reserva", e)
            }
        }
    }

    private fun fetchMenu(restaurantId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitRepository.getRetrofitInstance().create(APIProyecto::class.java).getRestaurantMenu(restaurantId)
                if (response.isSuccessful) {
                    _menu.value = response.body()
                } else {
                    Log.e("ReservationViewModel", "Error al obtener el menú: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ReservationViewModel", "Error al obtener el menú", e)
            }
        }
    }
}
