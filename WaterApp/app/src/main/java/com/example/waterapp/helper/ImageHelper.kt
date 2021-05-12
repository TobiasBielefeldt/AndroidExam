package com.example.waterapp.helper

import com.example.waterapp.R

class ImageHelper {
    companion object {
        //Method to get the image for a plant type.
        //Returns a "Place holder" image if the plant type is not recognised
        fun getImage(plantType: String?): Int {
        var returnValue : Int = R.drawable.imagemissing
            when(plantType)
            {
                "Cat-Plant" -> returnValue = R.drawable.some_kattemyter
                "Croton" -> returnValue = R.drawable.croton
                "Orchid" -> returnValue = R.drawable.orchid
                "Cactus" -> returnValue = R.drawable.cactus
            }

            return returnValue
        }
    }


}