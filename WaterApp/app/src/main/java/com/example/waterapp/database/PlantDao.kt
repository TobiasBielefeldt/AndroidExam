package com.example.waterapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlantDao {
    @Query("SELECT * FROM plant")
    fun getAllPlants(): List<Plant>

    @Query("SELECT * FROM plant WHERE Plant.uid = :id")
    fun getPlantFromID(id : String): Plant

    @Update
    fun update(plant: Plant): Int

    @Insert
    fun insert(plant: Plant)

    @Query("SELECT COUNT(*) FROM plant")
    fun count(): Int

    @Query("DELETE FROM plant")
    fun nukeTable()
}