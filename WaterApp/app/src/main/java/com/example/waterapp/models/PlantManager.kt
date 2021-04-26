package com.example.waterapp.models

class PlantManager {

    lateinit var plants: ArrayList<Plant>

    init {
        createPlants()
    }

    private fun createPlants() {
        plants = ArrayList()
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

    fun getPlantNames(): Array<String?> {
        val names = arrayOfNulls<String>(plants.size)

        plants.forEachIndexed { i, plant ->
            names[i] = plant.name
        }
        return names
    }

    fun getPlantDescription(): Array<String?> {
        val descriptions = arrayOfNulls<String>(plants.size)

        plants.forEachIndexed { i, plant ->
            descriptions[i] = plant.description
        }
        return descriptions
    }
}