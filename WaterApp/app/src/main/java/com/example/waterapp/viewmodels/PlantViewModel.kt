package com.example.waterapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.waterapp.R
import com.example.waterapp.database.Plant

class PlantViewModel : ViewModel(){
    private lateinit var plants: List<Plant>
    private lateinit var PlantNames: MutableList<String>
    private var selectedPlant = MutableLiveData<Pair<Int, Plant>>()
    private val PlantRepository = com.example.waterapp.repositories.PlantRepository

    init {
        loadPlants()
        loadPlantNames()
    }

    fun getSelectedPlant(): LiveData<Pair<Int, Plant>> {
        return selectedPlant
    }

    fun selectPlantAt(position: Int) {
        selectedPlant.value = Pair(position, plants[position])
    }

    fun getPlantNames(): MutableList<String> {
        return PlantNames
    }

    private fun loadPlantNames() {
        PlantNames = PlantRepository.getInstance().getAllPlantNames()
    }

    private fun loadPlants() {
        plants = PlantRepository.getInstance().getAllPlants()
    }

    fun getWaterDescription(waterNeed: Int): String{
        return when(waterNeed) {
            1 -> ("Should be watered every other month")
            2 -> ("Should be watered once month")
            3 -> ("Should be watered every other week")
            4 -> ("Should be watered once a week")
            5 -> ("Should be watered twice a week")
            else -> ("It is not yet known how much water this plant needs")
        }
    }

    fun getSunDescription(sunNeed: Int): String{
        return when(sunNeed) {
            1 -> ("None to Very little sun needed")
            2 -> ("Does not require direct sunlight")
            3 -> ("Need direct sunlight for at least an hour each day")
            4 -> ("Needs direct sunlight")
            5 -> ("This level of sun is not possible in Denmark")
            else -> ("It is not yet known how much sun this plant needs")
        }
    }

    fun getWaterImage(): Int{
        return R.drawable.water_drop
    }
}