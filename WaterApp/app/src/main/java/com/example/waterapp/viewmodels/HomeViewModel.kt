package com.example.waterapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waterapp.models.Plant
import com.example.waterapp.models.PlantManager

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment 2.0"
    }

    val text: LiveData<String> = _text

    private lateinit var plants: ArrayList<Plant>
    private lateinit var plantNames: Array<String?>
    private lateinit var plantDescriptions: Array<String?>
    private var selectedPlant = MutableLiveData<Pair<Int, Plant>>()
    private val plantManager = PlantManager()

    init {
        loadPersonalPlants()
        loadPlantNames()
    }

    fun getSelectedPersonalPlant(): LiveData<Pair<Int, Plant>> {
        return selectedPlant
    }

    fun selectPersonalPlantAt(position: Int) {
        selectedPlant.value = Pair(position, plants[position])
    }

    fun getNames(): Array<String?> {
        return plantNames
    }
    fun getDescriptions(): Array<String?> {
        return plantDescriptions
    }

    private fun loadPlantNames() {
        plantNames = plantManager.getPlantNames()
    }

    private fun loadPlantDescription() {
        plantDescriptions = plantManager.getPlantDescription()
    }

    private fun loadPersonalPlants() {
        plants = plantManager.plants
    }
}