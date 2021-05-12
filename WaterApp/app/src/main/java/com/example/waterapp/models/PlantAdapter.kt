package com.example.waterapp.models

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.waterapp.R
import com.example.waterapp.database.PersonalPlant
import com.example.waterapp.helper.ImageHelper
import com.example.waterapp.helper.TimeHelper
import com.example.waterapp.repositories.PersonalPlantRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PlantAdapter(private val plantList: MutableList<PersonalPlant>) : RecyclerView.Adapter<PlantAdapter.ItemViewHolder>() {

    //Provide a reference to the views for each data item

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val plantImage: ImageView = view.findViewById(R.id.plant_image)
        val plantName: TextView = view.findViewById(R.id.plant_name)
        val waterTime: TextView = view.findViewById(R.id.water_time)
        val btnWater: ImageButton = view.findViewById(R.id.btn_water)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.plant, parent, false)

        return ItemViewHolder(adapterLayout)
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val plant = plantList[position]
        holder.plantName.text = plant.personalName
        val time = TimeHelper.getTimeToWater(plant)
        holder.waterTime.text = time
        if(time[0] == '-') {
            holder.waterTime.setTextColor(Color.RED)
        }

        val personalPlantRepository = PersonalPlantRepository.getInstance()

        var btn = holder.btnWater

        //Button clicklistener, updates the plant with a new "last watered time"
        //And then calls notify to update the view
        btn.setOnClickListener{
            GlobalScope.launch {
                plant.lastWatered = System.currentTimeMillis()
                personalPlantRepository.update(plant)
            }
            notifyDataSetChanged()
        }


        // add plant images with Glide
        Glide.with(holder.itemView)
                .clear(holder.plantImage)
        Glide.with(holder.itemView)
                .load(ImageHelper.getImage(plant.plantType))
                .apply(RequestOptions().circleCrop())
                .into(holder.plantImage)

        // add watering button with Glide
        Glide.with(holder.btnWater)
                .clear(holder.btnWater)
        Glide.with(holder.itemView)
                .load(R.drawable.watering_can)
                .apply(RequestOptions().override(100, 100))
                .into(holder.btnWater)

    }


    //Return the size of your dataset (invoked by the layout manager)

    override fun getItemCount() = plantList.size

    //Removes an item from the dataset at the given position
    //Also calls "Notify" to update
    fun removeItem(position: Int) {
        plantList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, plantList.size)
    }

    //Since this class needs data from the database, and we can't run the database querys on the main thread
    //I was necessary to make a method that allows us to change the dataset when the database query is done
    fun updateItems(personalPlantList: MutableList<PersonalPlant>) {
        //Since we post them all at index 0 we do it in revers so the last one that gets added is at index 0, which is the first one in the dataset
        for(personalPlant in personalPlantList.reversed())
        {
            //Posting them all at index 0 was the simplest solution
            plantList.add(0,personalPlant)
            notifyItemInserted(0)
        }
    }
}