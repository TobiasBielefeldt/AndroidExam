package com.example.waterapp.database

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "personalPlant")
class PersonalPlant {
    @NonNull
    @PrimaryKey
    @JvmField
    var personalName: String? = null

    @JvmField
    @NonNull
    var plantType: String? = null

    //Plant size and pot size have an impact on how often you should water the plant
    @JvmField
    var plantSize: Int = 0
    @JvmField
    var potSize: Int = 0

    @JvmField
    var lastWatered: Long = 0

    companion object {

        //Test funktion, we don't use it anymore
        fun getBasicPlant(): PersonalPlant?
        {
            val plant = PersonalPlant()
            plant.plantType = "Croton"
            plant.personalName = "test"
            plant.plantSize = 2
            plant.potSize = 3
            plant.lastWatered = 4
            return plant
        }


    }
}