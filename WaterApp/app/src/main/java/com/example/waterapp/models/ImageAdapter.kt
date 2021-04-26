package com.example.waterapp.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.waterapp.R

class ImageAdapter (private val context: Context) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            var imageView: ImageView = v.findViewById(R.id.ivCell)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.image_layout, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val urlToCall = String.format("https://www.petworld.dk/media/695247/some_kattemyter.png", position)
            Glide.with(context)
                .clear(holder.imageView)
            Glide.with(context)
                .load(urlToCall)
                .apply(RequestOptions().circleCrop())
                .into(holder.imageView)
        }

        override fun getItemCount(): Int {
            return 50
        }
}
