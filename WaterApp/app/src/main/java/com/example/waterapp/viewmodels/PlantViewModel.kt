package com.example.waterapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waterapp.database.Plant
import com.example.waterapp.repositories.PlantRepository
import java.util.*

class PlantViewModel : ViewModel(){
    private lateinit var plants: List<Plant>
    private lateinit var plantNames: MutableList<String>

    private var selectedPlant:  MutableLiveData<Plant> = MutableLiveData()
    private val plantRepository = PlantRepository.getInstance()

    init {
        loadPlants()
        loadPlantNames()
    }

    fun getSelectedPlant(): MutableLiveData<Plant> {
        return selectedPlant
    }

    fun setSelectedPlant(name: String) {
        selectedPlant.value = plantRepository.getPlantFromName(name)!!
    }

    fun getPlantNames(): MutableList<String> {
        return plantNames
    }

    private fun loadPlantNames() {
        plantNames = plantRepository.getAllPlantNames()
    }

    private fun loadPlants() {
        plants = plantRepository.getAllPlants()
    }

    fun transformWaterNeed(waterNeed: Int): Pair<Int, String>{
        return when(waterNeed) {
            in 1..4 -> (Pair(5, "This plant should be watered twice a week"))
            in 5..9 -> (Pair(4, "This plant should be watered once a week"))
            in 10..14 -> (Pair(3, "This plant should be watered every other week"))
            in 15..21 -> (Pair(2, "This plant should be watered twice a month"))
            in 22..30 -> (Pair(1, "This plant should be watered once a month"))
            else -> (Pair(5, "Something went wrong"))
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
}