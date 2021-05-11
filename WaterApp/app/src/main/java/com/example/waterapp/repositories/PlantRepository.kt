package com.example.waterapp.repositories

import com.example.waterapp.database.Plant

class PlantRepository()
{
    private val plantDatabase: MutableList<Plant> = mutableListOf()
    //In a perfect world this would be in a database and then the first time you use the app it would download it
    init{
        insert(Plant.createNewPlant("Cat-Plant", "Origin: Egypt. This is a cat, not a plant",1,4))
        insert(Plant.createNewPlant("Croton", "Origin: South-East Asia. The croton is an easy-to-grow houseplant known for its variegated foliage covered in green, scarlet, orange, and yellow splotches.",4,4))
        insert(Plant.createNewPlant("Orchid" ,"Origin: Earth. The beauty, complexity and incredible diversity of orchid flowers are unrivalled in the plant world." , 4,2))
        insert(Plant.createNewPlant("Cactus", "Origin: Desert. Cacti are some of the most unusual and elegant plants in the world, with bold shapes of all kinds and beautiful green colour variations." , 5,2))
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

    fun getPlantFromName(plantName: String): Plant? {
        for(plant in plantDatabase)
        {
            if(plantName == plant.name)
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