package com.example.waterapp.models

class PlantManager {

    lateinit var plants: MutableList<Plant>

    init {
        createPlants()
    }

    private fun createPlants() {
        plants =  mutableListOf()

        plants.add(
                Plant(
                        "Orkide",
                        "a description")
        )
        plants.add(
                Plant(
                        "Cactus",
                        "Damn plant"
                )
        )
        plants.add(
                Plant(
                        "Monstera",
                        "This is another description"
                )
        )
    }

    fun getPlantNames(): MutableList<String> {
        val names = mutableListOf<String>()

        plants.forEach {plant ->
            names.add(plant.name)
        }
        return names
    }

    fun getPlantDescription(): MutableList<String> {
        val descriptions = mutableListOf<String>()

        plants.forEachIndexed { i, plant ->
            descriptions[i] = plant.description
        }
        return descriptions
    }
}