package com.example.loginapi.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginapi.api.APIProyecto
import com.example.loginapi.models.dto.MenuCategory
import com.example.loginapi.repositories.RetrofitRepository
import kotlinx.coroutines.launch

class RestaurantMenuViewModel : ViewModel() {
    private val _menu: MutableLiveData<List<MenuCategory>> = MutableLiveData()
    val menu: LiveData<List<MenuCategory>> get() = _menu

    fun fetchMenu(id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitRepository.getRetrofitInstance().create(APIProyecto::class.java).getRestaurantMenu(id)
                if (response.isSuccessful) {
                    _menu.value = response.body()
                    Log.d("RestaurantMenuViewModel", "Menú obtenido correctamente: ${response.body()}")
                } else {
                    Log.e("RestaurantMenuViewModel", "Error al obtener el menú: ${response.code()}")
                    // Manejar el error de la API, por ejemplo, mostrar un mensaje al usuario
                }
            } catch (e: Exception) {
                Log.e("RestaurantMenuViewModel", "Error al obtener el menú", e)
                // Manejar la excepción, por ejemplo, mostrar un mensaje de error al usuario
            }
        }
    }
}