package com.example.waterapp.database

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "personalPlant")
class PersonalPlant {
    @JvmField
    @PrimaryKey
    @NonNull
    var uid: String? = null

    @JvmField
    var personalName: String? = null

    @JvmField
    var plantSize: Int = 0

    @JvmField
    var potSize: Int = 0

    @JvmField
    var lastWatered: Int = 0

    companion object {

        fun getBasicPlant(): PersonalPlant?
        {
            val plant = PersonalPlant()
            plant.uid = UUID.randomUUID().toString()
            plant.personalName = "test"
            plant.plantSize = 2
            plant.potSize = 3
            plant.lastWatered = 4
            return plant
        }


    }
}