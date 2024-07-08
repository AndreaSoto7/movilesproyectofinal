package com.example.loginapi.models.dto

data class User(
    val name: String,
    val email: String,
    val password: String,
    val phone: String
)
