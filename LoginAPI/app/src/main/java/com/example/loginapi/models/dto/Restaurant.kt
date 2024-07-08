package com.example.loginapi.models.dto

data class Restaurant(
    val id: String,
    val name: String,
    val address: String,
    val city: String,
    val description: String,
    val user_id: Int,
    val logo: String,
    val photos: List<String>
) {
    data class RestaurantUpdateRequest(
        val name: String,
        val address: String,
        val city: String,
        val description: String
    )
}