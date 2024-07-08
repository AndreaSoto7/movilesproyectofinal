package com.example.loginapi.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginapi.R
import com.example.loginapi.databinding.ActivityMainBinding
import com.example.loginapi.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        checkToken()
        setupEventListeners()
        setupViewModelObservers()
    }

    private fun checkToken() {
        val token = PreferencesRepository.getToken(this)
        if (token != null) {
            Toast.makeText(this, "El token es: $token", Toast.LENGTH_SHORT).show()
            navigateToRestaurantList()
        }
    }

    private fun setupEventListeners() {
        binding.btnSignIn.setOnClickListener {
            val email = binding.txtEmail.text.toString()
            val password = binding.txtPassword.text.toString()
            model.login(email, password, this)
        }
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.btnGoRestaurants.setOnClickListener {
            navigateToRestaurantList()
        }
    }

    private fun setupViewModelObservers() {
        model.errorMessage.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        model.loginSuccess.observe(this) { success ->
            if (success) {
                navigateToRestaurantList()
            }
        }
    }

    private fun navigateToRestaurantList() {
        val intent = Intent(this, RestaurantListActivity::class.java)
        startActivity(intent)
        finish()
    }
}
