package com.example.waterapp.repositories

import android.content.Context
import androidx.room.Room
import com.example.waterapp.database.AppDatabase
import com.example.waterapp.database.PersonalPlant
import com.example.waterapp.database.PersonalPlantDao

class PersonalPlantRepository(private val personalPlantDao: PersonalPlantDao) {

    //Gets a list of all personalplants in the database
    fun getAllPlants(): List<PersonalPlant>{
        return personalPlantDao.getAllPersonalPlants()
    }

    //Gets a list of all personalplants in the database
    fun getAllPlantsOfSameType(type: String): List<PersonalPlant>{
        return personalPlantDao.getAllPersonalPlantsOfSameType(type)
    }

    //Inserts the personalplant
    fun insert(personalPlant: PersonalPlant)
    {
        personalPlantDao.insert(personalPlant)
    }

    //Gets a count of how many there is (This was mostly used for testing but I might be usefull)
    fun count(): Int {
        return personalPlantDao.countPlants()
    }

    //Updates a personalplant with new values
    fun update(personalPlant: PersonalPlant){
        personalPlantDao.update(personalPlant)
    }

    //Removes everything from the table
    fun nukeTable(){
        personalPlantDao.nukeTable()
    }

    fun delete(personalPlant: PersonalPlant)
    {
        personalPlantDao.delete(personalPlant)
    }

    //Takes a personalName an returns the personalplant
    fun getPersonalPlantFromPersonalName(personalPlantName: String): PersonalPlant{
        return personalPlantDao.getPersonalPlantFromPersonalName(personalPlantName)
    }

    fun checkPersonalName(name: String): Boolean{
        for(plant in getAllPlants()){
            if (plant.personalName == name){
                return false
            }
        }
        return true
    }

    companion object {
        // Singleton to prevent multiple instances from existing (It's also just really cool imo)
        private var INSTANCE: PersonalPlantRepository? = null

        fun getInstance(): PersonalPlantRepository {
            if (INSTANCE == null) {
                INSTANCE = PersonalPlantRepository(AppDatabase.getAppDatabase()!!.personalPlantDao())
            }
            return INSTANCE!!
        }
    }



}