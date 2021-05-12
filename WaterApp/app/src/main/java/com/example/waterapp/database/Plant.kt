package com.example.waterapp.database

import java.util.*

class Plant {
        //We decided to not make this into a room database as we did not feel like it was needed
        //This should properly all be stored in a database somewhere and then downloaded to the phone the first time you use the app
        //But since we already have firebase and database we think we will survive without making this stored online
        var name: String = ""
        var description: String? = null
        var sunNeed: Int = 0
        var waterNeed: Int = 0

        companion object {

            //Another test funktion that we dont use anymore
            fun getBasicPlant(): Plant?
            {
                val plant = Plant()
                plant.name = "test"
                plant.description = "This is a test plant"
                plant.sunNeed = 3
                plant.waterNeed = 4
                return plant
            }

            //Method to create new plants
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