package com.example.waterapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PersonalPlantDao {

    @Query("SELECT * FROM personalPlant")
    fun getAllPersonalPlants(): List<PersonalPlant>

    @Update
    fun update(personalPlant: PersonalPlant): Int

    @Insert
    fun insert(personalPlant: PersonalPlant)

    @Query("SELECT * FROM personalPlant WHERE personalPlantID = :id")
    fun getPersonalPlantFromID(id : String): PersonalPlant

    @Query("SELECT COUNT(*) FROM personalPlant")
    fun countPlants(): Int

    @Query("DELETE FROM personalPlant")
    fun nukeTable()

}