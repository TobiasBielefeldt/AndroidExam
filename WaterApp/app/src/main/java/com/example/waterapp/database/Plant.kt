package com.example.waterapp.database

import java.util.*

class Plant {
        //We decided to not make this into a room database as we did not feel like it was needed
        var name: String = ""
        var description: String? = null
        var sunNeed: Int = 0
        var waterNeed: Int = 0

        companion object {

            fun getBasicPlant(): Plant?
            {
                val plant = Plant()
                plant.name = "test"
                plant.description = "This is a test plant"
                plant.sunNeed = 3
                plant.waterNeed = 4
                return plant
            }

            fun createNewPlant(name: String, description: String, sunNeed : Int, waterNeed: Int): Plant
            {
                val plant = Plant()
                plant.name = name
                plant.description = description
                plant.sunNeed = sunNeed
                plant.waterNeed = waterNeed
                return plant
            }
        }
}