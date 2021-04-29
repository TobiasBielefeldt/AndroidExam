package com.example.waterapp.helper

import android.util.Log
import com.example.waterapp.R
import com.example.waterapp.database.AppDatabase
import com.example.waterapp.repositories.PlantRepository

class ImageHelper {
    companion object {
        //Retuns -1 if uid is not found
        fun getImage(uid: String?): Int {
        var returnValue : Int = R.drawable.imagemissing
            when(uid)
            {
                "Cat-Plant" -> returnValue = R.drawable.some_kattemyter
                "Croton" -> returnValue = R.drawable.croton
                "Orchid" -> returnValue = R.drawable.orchid
                "Cactus" -> returnValue = R.drawable.cactus
                else -> {
                    Log.w("Image", "Something is wrong I can feel it: $uid")
                }
            }


            return returnValue
        }
    }


}