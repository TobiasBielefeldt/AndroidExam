package com.example.waterapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waterapp.models.PersonalPlant
import com.example.waterapp.models.PersonalPlantsManager

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment 2.0"
    }

    val text: LiveData<String> = _text

    private lateinit var plants: ArrayList<PersonalPlant>
    private lateinit var plantNames: Array<String?>
    private lateinit var plantTimes: Array<Int?>
    private var selectedPlant = MutableLiveData<Pair<Int, PersonalPlant>>()
    private val personalPlantManager = PersonalPlantsManager()

    init {
        loadPersonalPlants()
        loadPlantNames()
    }

    fun getSelectedPersonalPlant(): LiveData<Pair<Int, PersonalPlant>> {
        return selectedPlant
    }

    fun selectPersonalPlantAt(position: Int) {
        selectedPlant.value = Pair(position, plants[position])
    }

    fun getNames(): Array<String?> {
        return plantNames
    }
    fun gettimes(): Array<Int?> {
        return plantTimes
    }

    private fun loadPlantNames() {
        plantNames = personalPlantManager.getPersonalPlantNames()
    }

    private fun loadPlantTimes() {
        plantTimes = personalPlantManager.getPersonalPlantTime()
    }

    private fun loadPersonalPlants() {
        plants = personalPlantManager.plants
    }
}