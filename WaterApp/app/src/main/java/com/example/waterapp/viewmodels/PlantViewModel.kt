package com.example.waterapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waterapp.models.Plant

class PlantViewModel : ViewModel(){
    private lateinit var plants: ArrayList<Plant>
    private lateinit var PlantNames: Array<String?>
    private var selectedPlant = MutableLiveData<Pair<Int, Plant>>()
    private val PlantManager = com.example.waterapp.models.PlantManager()

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

    fun getPlantNames(): Array<String?> {
        return PlantNames
    }

    private fun loadPlantNames() {
        PlantNames = PlantManager.getPlantNames()
    }

    private fun loadPlants() {
        plants = PlantManager.plants
    }
}