package com.example.loginapi.ui.adapters

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapi.databinding.ItemRestaurantBinding
import com.example.loginapi.models.dto.Restaurant
import com.example.loginapi.ui.activities.RestaurantDetailActivity
import com.example.loginapi.ui.activities.RestaurantListActivity

class RestaurantAdapter : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {
    private var restaurants: List<Restaurant> = emptyList()


    fun submitList(list: List<Restaurant>) {
        restaurants = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.bind(restaurant)

    }

    override fun getItemCount(): Int = restaurants.size
    inner class RestaurantViewHolder(private val binding: ItemRestaurantBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant) {
            binding.restaurantName.text = restaurant.name
            binding.restaurantAddress.text = restaurant.address
            binding.restaurantCity.text = restaurant.city
            binding.restaurantDescription.text = restaurant.description
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, RestaurantDetailActivity::class.java)
                intent.putExtra("restaurant_id", restaurant.id.toInt())
                intent.putExtra("restaurant_name", restaurant.name)
                intent.putExtra("restaurant_description", restaurant.description)
                if (itemView.context is Activity) {
                    (itemView.context as Activity).startActivityForResult(
                        intent,
                        RestaurantListActivity.UPDATE_REQUEST_CODE
                    )
                } else {
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

}