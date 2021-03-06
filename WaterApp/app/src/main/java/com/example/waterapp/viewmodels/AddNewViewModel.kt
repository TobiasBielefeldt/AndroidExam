package com.example.waterapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.waterapp.database.PersonalPlant
import com.example.waterapp.repositories.PersonalPlantRepository

class AddNewViewModel : ViewModel() {

    private var repository: PersonalPlantRepository = PersonalPlantRepository.getInstance()
    private var plant: PersonalPlant = PersonalPlant()
    private lateinit var plantType: String
    private var potSize = 2;
    private var plantSize = 2;
    private lateinit var personalName: String;

    fun setPotSize(size: String){
        potSize = when(size) {
            "small" -> 1
            "medium" -> 2
            "large" -> 3
            else -> 2
        }
    }

    fun setPlantSize(size: String){
        plantSize = when(size) {
            "small" -> 1
            "medium" -> 2
            "large" -> 3
            else -> 2
        }
    }

    fun setName(name: String, type: String){
        personalName = name
        plantType = type
    }

    fun getName(): String {
        return personalName
    }

    fun createNewPersonalPlant(){
        plant.personalName = personalName
        plant.plantSize = plantSize
        plant.potSize = potSize
        plant.lastWatered = System.currentTimeMillis()
        plant.plantType = plantType
        repository.insert(plant)
    }
}