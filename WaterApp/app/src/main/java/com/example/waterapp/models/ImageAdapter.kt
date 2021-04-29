package com.example.waterapp.models

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.waterapp.R
import com.example.waterapp.database.Plant
import com.example.waterapp.helper.ImageHelper
import com.example.waterapp.repositories.PlantRepository


class ImageAdapter (private val context: Context) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            var imageView: ImageView = v.findViewById(R.id.ivCell)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.image_layout, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val plantRepository = PlantRepository.getInstance()
            var plant: Plant? = null

            //This takes the first plant
            for(p in plantRepository.getAllPlants())
            {
                plant = p
                break
            }

            //This gets the image for the first plant using id (Just test uses)
            val image = ImageHelper.getImage(plant!!.uid)

            Glide.with(context)
                    .clear(holder.imageView)
            Glide.with(context)
                    .load(image)
                    .apply(RequestOptions().circleCrop())
                    .into(holder.imageView)
        }

        override fun getItemCount(): Int {
            return 50
        }
}
