package com.example.loginapi.ui.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginapi.R

class GalleryAdapter(private val images: List<String>) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gallery_image, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        //val imageUrl = images[position]
        //Glide.with(holder.itemView.context)
        //    .load(imageUrl)
          //  .into(holder.galleryImage)
    }

    override fun getItemCount(): Int = images.size

    inner class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val galleryImage: ImageView = itemView.findViewById(R.id.galleryImage)
    }
}