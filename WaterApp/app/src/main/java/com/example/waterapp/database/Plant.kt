package com.example.waterapp.database

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "plant")
class Plant {

        @JvmField
        @PrimaryKey
        @NonNull
        var uid: String? = null

        @JvmField
        var name: String? = null

        @JvmField
        var description: String? = null

        @JvmField
        var sunNeed: Int = 0

        @JvmField
        var waterNeed: Int = 0

        companion object {

            fun getBasicPlant(): Plant?
            {
                val plant = Plant()
                plant.uid = UUID.randomUUID().toString()
                plant.name = "test"
                plant.description = "This is a test plant"
                plant.sunNeed = 3
                plant.waterNeed = 4
                return plant
            }


        }

}