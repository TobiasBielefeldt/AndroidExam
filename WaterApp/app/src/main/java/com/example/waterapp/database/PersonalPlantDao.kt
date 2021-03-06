package com.example.waterapp.database

import androidx.room.*

@Dao
interface PersonalPlantDao {

    //All querys should be self explanatory

    @Query("SELECT * FROM personalPlant")
    fun getAllPersonalPlants(): List<PersonalPlant>

    @Query("SELECT * FROM personalPlant WHERE plantType = :type")
    fun getAllPersonalPlantsOfSameType(type: String): List<PersonalPlant>

    @Update
    fun update(personalPlant: PersonalPlant): Int

    @Insert
    fun insert(personalPlant: PersonalPlant)

    @Query("SELECT * FROM personalPlant WHERE personalName = :name")
    fun getPersonalPlantFromPersonalName(name : String): PersonalPlant

    @Query("SELECT COUNT(*) FROM personalPlant")
    fun countPlants(): Int

    @Query("DELETE FROM personalPlant")
    fun nukeTable()

    @Delete
    fun delete(personalPlant: PersonalPlant)

}