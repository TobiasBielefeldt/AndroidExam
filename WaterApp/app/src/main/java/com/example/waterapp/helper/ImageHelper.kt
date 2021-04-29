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
                "9157751e-0708-4621-a73d-58d4ebd8c824" -> returnValue = R.drawable.some_kattemyter
                "aad3a4b3-f7d9-4903-bb78-7a65f70d9cfc" -> returnValue = R.drawable.croton
                "15d68476-c432-413f-9ca3-0533f44234a8" -> returnValue = R.drawable.orchid
                "4835fb84-d604-4d6a-8469-0dbb11ef62f7" -> returnValue = R.drawable.cactus
                else -> {
                    Log.w("Image", "Something is wrong I can feel it: $uid")
                }
            }


            return returnValue
        }
    }


}