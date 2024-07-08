package com.example.loginapi.models.dto

data class MenuCategory(
    val id: Int,
    val name: String,
    val restaurant_id: Int,
    val description: String, // Nueva propiedad
    val plates: List<Plate> = emptyList()
)