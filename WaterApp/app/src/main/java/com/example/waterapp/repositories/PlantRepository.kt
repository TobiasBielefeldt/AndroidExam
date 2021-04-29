package com.example.waterapp.repositories

import com.example.waterapp.database.Plant

class PlantRepository()
{
    private val plantDatabase: MutableList<Plant> = mutableListOf()
    init{
        insert(Plant.createNewPlant("9157751e-0708-4621-a73d-58d4ebd8c824","Cat-Plant", "This is a cat, not a plant",1,4))
        insert(Plant.createNewPlant("aad3a4b3-f7d9-4903-bb78-7a65f70d9cfc","Croton", "The croton is an easy-to-grow houseplant known for its variegated foliage covered in green, scarlet, orange, and yellow splotches.",4,4))
        insert(Plant.createNewPlant("15d68476-c432-413f-9ca3-0533f44234a8", "Orchid" ,"The beauty, complexity and incredible diversity of orchid flowers are unrivalled in the plant world. These exotic beauties comprise the largest family of flowering plants on earth, with over 30,000 different species, and at least 200,000 hybrids." , 4,2))
        insert(Plant.createNewPlant("4835fb84-d604-4d6a-8469-0dbb11ef62f7","Cactus", "Cacti are some of the most unusual and elegant plants in the world, with bold shapes of all kinds and beautiful green colour variations." , 5,2))
    }


    //This Repository works the same way as the PersonalPlantRepository so look at that if you need help
    fun getAllPlants(): List<Plant>{
        return plantDatabase
    }

    fun getAllPlantNames(): MutableList<String> {
        val names = mutableListOf<String>()
        getAllPlants().forEach {plant ->
            names.add(plant.name.toString())
        }
        return names
    }

    fun insert(plant: Plant)
    {
        plantDatabase.add(plant)
    }

    fun count(): Int {
        return plantDatabase.size
    }

    fun nukeTable(){
        plantDatabase.clear()
    }

    fun getPlantFromID(plantID: String): Plant? {
        for(plant in plantDatabase)
        {
            if(plantID == plant.uid)
            {
                return plant
            }
        }
        return null
    }

    companion object {
        // Singleton to prevent multiple instances from existing
        private var INSTANCE: PlantRepository? = null

        fun getInstance(): PlantRepository {
            if (INSTANCE == null) {
                INSTANCE = PlantRepository()
            }
            return INSTANCE!!
        }
    }

}