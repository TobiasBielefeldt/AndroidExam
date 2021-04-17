package com.example.waterapp.models

class PersonalPlantsManager {

    lateinit var plants: ArrayList<PersonalPlant>

    init {
        createPersonalPlants()
    }

    private fun createPersonalPlants() {
        plants = ArrayList()
        plants.add(
                PersonalPlant(
                        "Orkide",
                        5)
        )
        plants.add(
                PersonalPlant(
                        "Cactus",
                        2
                )
        )
        plants.add(
                PersonalPlant(
                        "Monstera",
                        8
                )
        )
    }

    fun getPersonalPlantNames(): Array<String?> {
        val names = arrayOfNulls<String>(plants.size)

        plants.forEachIndexed { i, plant ->
            names[i] = plant.name
        }
        return names
    }

    fun getPersonalPlantTime(): Array<Int?> {
        val times = arrayOfNulls<Int>(plants.size)

        plants.forEachIndexed { i, plant ->
            times[i] = plant.time
        }
        return times
    }
}