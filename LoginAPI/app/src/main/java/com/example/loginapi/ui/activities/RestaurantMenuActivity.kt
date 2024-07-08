package com.example.loginapi.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginapi.databinding.ActivityRestaurantMenuBinding
import com.example.loginapi.ui.adapters.MenuCategoryAdapter
import com.example.loginapi.ui.viewmodels.RestaurantMenuViewModel

class RestaurantMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantMenuBinding
    private lateinit var menuCategoryAdapter: MenuCategoryAdapter
    private val viewModel: RestaurantMenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menuCategoryAdapter = MenuCategoryAdapter()
        binding.menuCategories.layoutManager = LinearLayoutManager(this)
        binding.menuCategories.adapter = menuCategoryAdapter

        val restaurantId = intent.getIntExtra("restaurant_id", -1)
        Log.d("RestaurantMenuActivity", "Restaurant ID recibido: $restaurantId")
        if (restaurantId != -1) {
            viewModel.fetchMenu(restaurantId)
            binding.restaurantName.text = "Menú del Restaurante $restaurantId" // O el nombre real si lo tienes
        } else {
            Log.e("RestaurantMenuActivity", "Error: No se recibió el ID del restaurante.")
            // Manejar el caso en que no se recibe el ID, por ejemplo, mostrar un mensaje al usuario
        }

        viewModel.menu.observe(this) { menuCategories ->
            Log.d("RestaurantMenuActivity", "Menú recibido: $menuCategories")
            menuCategoryAdapter.submitList(menuCategories)
        }
    }
}