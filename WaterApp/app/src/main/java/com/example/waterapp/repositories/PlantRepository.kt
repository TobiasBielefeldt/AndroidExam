package com.example.waterapp.repositories


import com.example.waterapp.database.AppDatabase
import com.example.waterapp.database.Plant
import com.example.waterapp.database.PlantDao

class PlantRepository(private val plantDao: PlantDao)
{

    //This Repository works the same way as the PersonalPlantRepository so look at that if you need help
    fun getAllPlants(): List<Plant>{
        return plantDao.getAllPlants()
    }

    fun insert(plant: Plant)
    {
        plantDao.insert(plant)
    }

    fun count(): Int {
        return plantDao.count()
    }

    fun update(plant: Plant){
        plantDao.update(plant)
    }

    fun nukeTable(){
        plantDao.nukeTable()
    }

    fun getPlantFromID(plantID: String): Plant {
        return plantDao.getPlantFromID(plantID)
    }

    companion object {
        // Singleton to prevent multiple instances from existing
        private var INSTANCE: PlantRepository? = null

        fun getInstance(): PlantRepository {
            if (INSTANCE == null) {
                INSTANCE = PlantRepository(AppDatabase.getAppDatabase()!!.plantDao())
            }
            return INSTANCE!!
        }
    }


}