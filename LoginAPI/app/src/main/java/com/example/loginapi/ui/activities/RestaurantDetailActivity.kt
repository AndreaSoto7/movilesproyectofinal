package com.example.loginapi.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapi.R
import com.example.loginapi.databinding.ActivityRestaurantDetailBinding
import com.example.loginapi.models.dto.Restaurant
import com.example.loginapi.ui.viewmodels.RestaurantDetailViewModel

class RestaurantDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantDetailBinding
    private val viewModel: RestaurantDetailViewModel by viewModels()

    companion object {
        const val MODE_INSERT = "INSERT"
        const val MODE_VIEW = "VIEW"
    }

    private var mode: String = MODE_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mode = intent.getStringExtra("mode") ?: MODE_VIEW

        if (mode == MODE_INSERT) {
            // Ocultar botones innecesarios
            binding.btnViewMenu.visibility = View.GONE
            binding.btnReserve.visibility = View.GONE
            binding.btnUpdateRestaurant.text = "Insertar Restaurante"
            binding.btnDeleteRestaurant.visibility = View.GONE // Ocultar botón Eliminar
        }

        val restaurantId = intent.getIntExtra("restaurant_id", -1)
        val restaurantName = intent.getStringExtra("restaurant_name")
        val restaurantDescription = intent.getStringExtra("restaurant_description")
        val logo = intent.getStringExtra("restaurant_logo")
        val userId = intent.getIntExtra("restaurant_user_id", 0)
        binding.restaurantName.setText(restaurantName)
        binding.restaurantDescription.setText(restaurantDescription)
        binding.btnDeleteRestaurant.setOnClickListener {
            val restaurantId = intent.getIntExtra("restaurant_id", -1)
            if (restaurantId != -1) {
                // Mostrar diálogo de confirmación
                AlertDialog.Builder(this)
                    .setTitle("Confirmar eliminación")
                    .setMessage("¿Estás seguro de que quieres eliminar este restaurante?")
                    .setPositiveButton("Eliminar") { _, _ ->
                        val token = getAuthToken(this)
                        if (token != null) {
                            viewModel.deleteRestaurant(restaurantId, token)
                            // Manejar la respuesta de la eliminación (por ejemplo, mostrar un Toast y terminar la actividad)
                            Toast.makeText(this, "Restaurante eliminado", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Error: No se pudo obtener el token", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            } else {
                Toast.makeText(this, "Error: No se recibió el ID del restaurante", Toast.LENGTH_SHORT).show()
            }
        }

        val viewMenuButton = findViewById<Button>(R.id.btn_view_menu)
        val reservaButton = findViewById<Button>(R.id.btn_reserve)
        val updateButton = findViewById<Button>(R.id.btn_update_restaurant)

        viewMenuButton.setOnClickListener {
            val intent = Intent(this, RestaurantMenuActivity::class.java)
            intent.putExtra("restaurant_id", restaurantId)
            startActivity(intent)
        }

        reservaButton.setOnClickListener {
            if (restaurantId != -1) {
                val intent = Intent(this, ReservationActivity::class.java)
                intent.putExtra("restaurant_id", restaurantId) // Pasa el ID del restaurante
                startActivity(intent)
            } else {
                // Manejo de errores si no se recibe el ID del restaurante
                Toast.makeText(this, "Error: No se recibió el ID del restaurante", Toast.LENGTH_SHORT).show()
            }
        }

        // Deshabilitar edición si no hay token
        val token = getAuthToken(this)
        if (token == null) {
            binding.restaurantName.isEnabled = false
            binding.restaurantDescription.isEnabled = false
            binding.restaurantAddress.isEnabled = false
            binding.restaurantCity.isEnabled = false
            binding.btnUpdateRestaurant.visibility = Button.GONE
        }

        updateButton.setOnClickListener {
            if (mode == MODE_INSERT) {
                val newName = binding.restaurantName.text.toString()
                val newAddress = binding.restaurantAddress.text.toString()
                val newCity = binding.restaurantCity.text.toString()
                val newDescription = binding.restaurantDescription.text.toString()

                val token = getAuthToken(this)
                if (token != null) {
                    RestaurantDetailViewModel().insertRestaurant(
                        token,
                        newName,
                        newAddress,
                        newCity,
                        newDescription
                    )
                    Toast.makeText(this, "Restaurante insertado", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "Error: No se pudo obtener el token", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            val updatedName = binding.restaurantName.text.toString()
            val updatedAddress = binding.restaurantAddress.text.toString()
            val updatedCity = binding.restaurantCity.text.toString()
            val updatedDescription = binding.restaurantDescription.text.toString()

            if (token != null) {
                viewModel.updateRestaurant(
                    restaurantId,
                    token,
                    updatedName,
                    updatedAddress,
                    updatedCity,
                    updatedDescription
                )
                Toast.makeText(this, "Restaurante actualizado", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Error: No se pudo obtener el token de autorización",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        if (restaurantId != -1) {
            viewModel.getRestaurantDetails(restaurantId)
        }

        viewModel.restaurantDetails.observe(this) { restaurant ->
            binding.restaurantName.setText(restaurant.name)
            binding.restaurantDescription.setText(restaurant.description)
            binding.restaurantAddress.setText(restaurant.address)
            binding.restaurantCity.setText(restaurant.city)
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun getAuthToken(context: Context): String? {
        return PreferencesRepository.getToken(context)
    }

}