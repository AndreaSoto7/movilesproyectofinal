package com.example.loginapi.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginapi.api.APIProyecto
import com.example.loginapi.models.dto.User
import com.example.loginapi.repositories.RetrofitRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val _registrationResult = MutableLiveData<Response<User>>()
    val registrationResult: LiveData<Response<User>> get() = _registrationResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun registerUser(user: User) {
        viewModelScope.launch {
            try {
                val response = RetrofitRepository.getRetrofitInstance().create(APIProyecto::class.java).registerUser(user)
                _registrationResult.postValue(response)
            } catch (e: Exception) {
                _errorMessage.postValue("Error registering user: ${e.message}")
                Log.e("UserViewModel", "Error registering user", e)
            }
        }
    }
}
