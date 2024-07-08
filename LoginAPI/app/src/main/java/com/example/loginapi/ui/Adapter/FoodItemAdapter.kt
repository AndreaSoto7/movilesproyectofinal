package com.example.loginapi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapi.databinding.ItemPlateBinding
import com.example.loginapi.models.dto.Plate

class FoodItemAdapter : RecyclerView.Adapter<FoodItemAdapter.PlateViewHolder>() {
    private var plates: List<Plate> = emptyList()

    fun submitList(list: List<Plate>) {
        plates = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlateViewHolder {
        val binding = ItemPlateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlateViewHolder, position: Int) {
        holder.bind(plates[position])
    }

    override fun getItemCount(): Int = plates.size

    inner class PlateViewHolder(private val binding: ItemPlateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(plate: Plate) {
            binding.plateName.text = plate.name
            binding.plateDescription.text = plate.description
            binding.platePrice.text = plate.price
            // ... otros bindings según tu layout item_plate.xml
        }
    }
}