package com.example.loginapi.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginapi.databinding.ActivityReservationBinding
import com.example.loginapi.ui.adapters.FoodItemAdapter
import com.example.loginapi.ui.viewmodels.ReservationViewModel

class ReservationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationBinding
    private lateinit var foodItemAdapter: FoodItemAdapter
    private val viewModel: ReservationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodItemAdapter = FoodItemAdapter()
        binding.foodItems.layoutManager = LinearLayoutManager(this)
        binding.foodItems.adapter = foodItemAdapter

        val restaurantId = intent.getIntExtra("restaurant_id", -1)
        val reservationId = intent.getIntExtra("reservation_id", -1)

        Log.d("ReservationActivity", "Restaurant ID recibido: $restaurantId")
        Log.d("ReservationActivity", "Reservation ID recibido: $reservationId")

        if (restaurantId != -1) {
            viewModel.fetchRestaurantDetails(restaurantId)
        } else {
            Log.e("ReservationActivity", "Error: No se recibió el ID del restaurante.")
            Toast.makeText(this, "Error al obtener la información del restaurante", Toast.LENGTH_SHORT).show()
            finish() // Terminar la actividad si no se recibe el ID
        }

        if (reservationId != -1) {
            viewModel.fetchReservationDetails(reservationId) // Asegúrate de pasar el reservationId
        } else {
            Log.e("ReservationActivity", "Error: No se recibió el ID de la reserva.")
            Toast.makeText(this, "Error al obtener la información de la reserva", Toast.LENGTH_SHORT).show()
        }

        // Observer para los detalles del restaurante
        viewModel.restaurantDetails.observe(this) { restaurant ->
            binding.restaurantName.text = restaurant.name
            // ... otros detalles del restaurante que quieras mostrar
        }

        // Observer para los detalles de la reserva
        viewModel.reservationDetails.observe(this) { reservation ->
            reservation?.let {
                Log.d("ReservationActivity", "Detalles de la reserva recibidos: $reservation")

            } ?: run {
                Log.e("ReservationActivity", "Error: Detalles de la reserva son nulos.")
            }
        }

        // Observer para el menú
        viewModel.menu.observe(this) { menuCategories ->
            val foodItems = menuCategories.flatMap { it.plates }
            foodItemAdapter.submitList(foodItems)
        }
    }
}

