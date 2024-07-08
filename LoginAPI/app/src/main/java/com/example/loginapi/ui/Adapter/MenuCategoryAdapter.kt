package com.example.loginapi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapi.databinding.ItemMenuCategoryBinding
import com.example.loginapi.models.dto.MenuCategory

class MenuCategoryAdapter : RecyclerView.Adapter<MenuCategoryAdapter.MenuCategoryViewHolder>() {
    private var menuCategories: List<MenuCategory> = emptyList()

    fun submitList(list: List<MenuCategory>) {
        menuCategories = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuCategoryViewHolder {
        val binding = ItemMenuCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuCategoryViewHolder, position: Int) {
        holder.bind(menuCategories[position])
    }

    override fun getItemCount(): Int = menuCategories.size

    inner class MenuCategoryViewHolder(private val binding: ItemMenuCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menuCategory: MenuCategory) {
            binding.categoryName.text = menuCategory.name
            binding.categoryDescription.text = menuCategory.description
        }
    }
}

