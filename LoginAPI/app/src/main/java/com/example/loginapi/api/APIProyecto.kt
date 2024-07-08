// APIProyecto.kt
package com.example.loginapi.api

import com.example.loginapi.models.dto.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIProyecto {
    @POST("loginuser")
    fun login(@Body loginRequest: LoginRequestDTO): Call<LoginResponseDTO>

    @POST("/api/registeruser")
    suspend fun registerUser(@Body user: User): Response<User>

    @POST("restaurants")
    suspend fun insertRestaurant(@Header("Authorization") token: String, @Body restaurant: Map<String, String>): Response<Restaurant>

    @DELETE("api/restaurants/{restaurantId}")
    suspend fun deleteRestaurant(@Header("Authorization") token: String, @Path("restaurantId") restaurantId: Int): Response<Void>

    @POST("restaurants/search")
    fun searchRestaurants(@Body searchParams: Map<String, String>): Call<List<Restaurant>>

    @GET("restaurants/{id}/menu")
    suspend fun getRestaurantMenu(@Path("id") id: Int): Response<List<MenuCategory>>

    @GET("/restaurants/{restaurantId}/reservations")
    suspend fun getReservationDetails(@Path("restaurantId") restaurantId: Int): Response<List<Reservation>>

    @GET("restaurants/{id}")
    suspend fun getRestaurantDetails(@Path("id") restaurantId: Int): Response<Restaurant>

    @PUT("restaurants/{id}")
    suspend fun updateRestaurant(
        @Path("id") id: Int,
        @Header("Authorization") authToken: String,
        @Body restaurant: Restaurant.RestaurantUpdateRequest
    ): Response<Void>


    //reservas
    @POST("reservations")
    fun createReservation(@Header("Authorization") token: String, @Body reservation: Reservation): Call<Reservation>
    @GET("reservations/{id}")
    fun getReservation(@Header("Authorization") token: String, @Path("id") id: Int): Call<Reservation>
    @GET("reservations")
    suspend fun getReservations(
        @Header("Authorization") token: String
    ): Response<List<Reservation>>

    @POST("reservations/{id}/cancel")
    fun cancelReservation(@Header("Authorization") token: String, @Path("id") id: Int): Call<Void>
    @GET("api/restaurants/{restaurantId}/reservations") // Ajusta la ruta según tu API
    suspend fun getReservationsByRestaurant(
        @Header("Authorization") authToken: String,
        @Path("restaurantId") restaurantId: Int
    ): Response<List<Reservation>>
}
