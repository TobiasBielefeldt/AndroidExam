package com.example.waterapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PersonalPlant::class], version = 7, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personalPlantDao(): PersonalPlantDao

    companion object {
        // Singleton to prevent multiple instances from existing
        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "plant-database")
                    // Allow queries on the main thread.
                    // Don't do this on a real app!
                    //.allowMainThreadQueries
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }



        fun getAppDatabase(): AppDatabase?{
            return INSTANCE
        }
    }
}