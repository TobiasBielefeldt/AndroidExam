package com.example.waterapp.models

import android.provider.Settings
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.waterapp.R
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.waterapp.database.PersonalPlant
import com.example.waterapp.helper.ImageHelper
import com.example.waterapp.helper.TimeHelper
import com.example.waterapp.repositories.PersonalPlantRepository
import com.example.waterapp.views.HomeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

/**
 * Adapter for the [RecyclerView] in [HomeFragment]. Displays [Affirmation] data object.
 */
class PlantAdapter(private val plantList: List<PersonalPlant>) : RecyclerView.Adapter<PlantAdapter.ItemViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val plantImage: ImageView = view.findViewById(R.id.plant_image)
        val plantName: TextView = view.findViewById(R.id.plant_name)
        val waterTime: TextView = view.findViewById(R.id.water_time)
        val btnWater: ImageButton = view.findViewById(R.id.btn_water)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.plant, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val plant = plantList[position]
        holder.plantName.text = plant.personalName
        holder.waterTime.text = TimeHelper.getTimeToWater(plant)

        val personalPlantRepository = PersonalPlantRepository.getInstance()

        var btn = holder.btnWater

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

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = plantList.size

    fun removeAt(position: Int) {
        plantList.drop(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, plantList.size)
    }
}