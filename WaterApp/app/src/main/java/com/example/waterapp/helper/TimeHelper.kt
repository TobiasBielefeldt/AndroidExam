package com.example.waterapp.helper

import android.util.Log
import com.example.waterapp.database.PersonalPlant
import com.example.waterapp.repositories.PlantRepository
import kotlinx.coroutines.newFixedThreadPoolContext
import java.util.concurrent.TimeUnit
import android.util.Log.w as w1

class TimeHelper {
    companion object {
        //Retuns -1 if plant not found
        fun getTimeToWater(plant: PersonalPlant): String {
            val lastWatered = plant.lastWatered
            val potSize = plant.potSize
            val plantSize = plant.plantSize
            val waterNeed = PlantRepository.getInstance().getPlantFromName(plant.plantType.toString())!!.waterNeed

            val calculatedNeed = calculatePersonalWaterNeed(waterNeed, potSize, plantSize)

            val timeSinceLastWatered = System.currentTimeMillis() - lastWatered

            val resultInMillis = calculatedNeed-timeSinceLastWatered

            val days  = TimeUnit.MILLISECONDS.toDays(resultInMillis)
            val hours= TimeUnit.MILLISECONDS.toHours(resultInMillis)- TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(resultInMillis))
            val minutes = TimeUnit.MILLISECONDS.toMinutes(resultInMillis)- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(resultInMillis))

            return "$days:$hours:$minutes"
        }

        // returns how often the plant should be watered in days(for example every 2 days)
        // then translates that to milliseconds
        private fun calculatePersonalWaterNeed(waterNeed: Int, potSize: Int, plantSize: Int): Int{
            val aDayInMilli = 86400000
            return (waterNeed*potSize/plantSize)*aDayInMilli
        }
    }
}