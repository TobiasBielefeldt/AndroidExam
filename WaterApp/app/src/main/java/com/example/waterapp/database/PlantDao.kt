package com.example.waterapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlantDao {
    @Query("SELECT * FROM plant")
    fun getPlants(): Plant

    @Update
    fun update(plant: Plant): Int

    @Insert
    fun insert(plant: Plant)

    @Query("SELECT COUNT(*) FROM plant")
    fun countPlants(): Int

    @Query("DELETE FROM plant")
    fun nukeTable()
}