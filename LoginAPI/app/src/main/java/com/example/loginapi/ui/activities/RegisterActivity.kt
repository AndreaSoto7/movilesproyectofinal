package com.example.loginapi.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapi.databinding.ActivityRegisterBinding
import com.example.loginapi.models.dto.User
import com.example.loginapi.ui.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val phone = binding.etPhone.text.toString()

            if (isValidInput(name, email, password, phone)) {
                val user = User(name, email, password, phone)
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.registerUser(user)
                }
            } else {
                Toast.makeText(this, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.registrationResult.observe(this) { response ->
            if (response.isSuccessful) {
                Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
                navigateToLogin()
            } else {
                val errorMessage = getErrorMessage(response.code())
                Toast.makeText(this, "Registration failed: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidInput(name: String, email: String, password: String, phone: String): Boolean {
        // Agrega aquí tus validaciones específicas
        return name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && phone.isNotEmpty()
    }

    private fun getErrorMessage(errorCode: Int): String {
        // Agrega aquí la lógica para obtener un mensaje de error más informativo
        return when (errorCode) {
            400 -> "Bad Request"
            409 -> "Email already registered"
            else -> "Registration failed"
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}