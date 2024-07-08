package com.example.loginapi.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapi.R
import com.example.loginapi.databinding.ActivityRestaurantListBinding
import com.example.loginapi.ui.adapters.RestaurantAdapter
import com.example.loginapi.ui.viewmodels.RestaurantViewModel

class RestaurantListActivity : AppCompatActivity() {
    private val viewModel: RestaurantViewModel by viewModels()
    private lateinit var searchParams: MutableMap<String, String>
    private lateinit var binding: ActivityRestaurantListBinding

    companion object {
        const val UPDATE_REQUEST_CODE = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UPDATE_REQUEST_CODE && resultCode == RESULT_OK) {
            viewModel.fetchRestaurants(searchParams)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_restaurants)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RestaurantAdapter()
        recyclerView.adapter = adapter

        searchParams = mutableMapOf(
            "city" to "",
            "date" to "",
            "time" to ""
        )

        val token =
            PreferencesRepository.getToken(this) // Reemplaza con tu forma de obtener el token
        if (token == null) {
            findViewById<TextView>(R.id.btnLogout).text = "Volver a inicio"
            findViewById<Button>(R.id.btnInsertRestaurant).visibility = View.GONE
        }

        viewModel.fetchRestaurants(searchParams)

        viewModel.restaurants.observe(this) { restaurants ->
            adapter.submitList(restaurants)
        }

        val editTextCity = findViewById<EditText>(R.id.txtvCityFilter)
        editTextCity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                searchParams["city"] = s.toString()
                viewModel.fetchRestaurants(searchParams)
            }
        })

        val editTextDate = findViewById<EditText>(R.id.txtvDateFilter)
        editTextDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                searchParams["date"] = s.toString()
                viewModel.fetchRestaurants(searchParams)
            }
        })

        val editTextTime = findViewById<EditText>(R.id.txtvTimeFilter)
        editTextTime.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                searchParams["time"] = s.toString()
                viewModel.fetchRestaurants(searchParams)
            }
        })

        binding.btnLogout.setOnClickListener {
            val currentToken =
                PreferencesRepository.getToken(this) // Reemplaza con tu forma de obtener el token
            if (currentToken == null) {
                // Usuario no logueado, volver a MainActivity
                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                ) // Reemplaza MainActivity con el nombre de tu actividad principal
                finish() // Cierra la actividad actual
            } else {
                // Usuario logueado, cerrar sesión
                PreferencesRepository.clearToken(this) // Reemplaza con tu forma de borrar el token
                Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                ) // Reemplaza MainActivity con el nombre de tu actividad principal
                finish() // Cierra la actividad actual
            }
        }
        binding.btnInsertRestaurant.setOnClickListener {
            val intent = Intent(this, RestaurantDetailActivity::class.java)
            intent.putExtra("mode", RestaurantDetailActivity.MODE_INSERT)
            startActivity(intent)
        }
    }
}

