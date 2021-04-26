package com.example.waterapp.repositories

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseRepository {
    private val rootNode: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val plants = rootNode.getReference("Plants")

    //Updates the value with the plantID or creates a new one of the ID does not exist
    fun insertOrUpdate(plantID: String, number: Int)
    {
        plants.child(plantID).setValue(number)
    }

    //Takes the string and gets the current value then increments it by 1 (It does this with a listener so I don't think a thread is needed)
    fun incrementPlant(plantID: String)
    {
        val completeListener = object : OnCompleteListener<DataSnapshot> {
            override fun onComplete(p0: Task<DataSnapshot>) {
                if (p0.isSuccessful)
                {
                    insertOrUpdate(plantID, p0.result?.value.toString().toInt() + 1)
                }
            }
        }

        plants.child(plantID).get().addOnCompleteListener(completeListener)
    }

    fun decrementPlan(plantID: String)
    {
        val completeListener = object : OnCompleteListener<DataSnapshot> {
            override fun onComplete(p0: Task<DataSnapshot>) {
                if (p0.isSuccessful)
                {
                    insertOrUpdate(plantID, p0.result?.value.toString().toInt() - 1)
                }
            }
        }

        plants.child(plantID).get().addOnCompleteListener(completeListener)
    }

    //This is used to create your own onComplete/Failure/Change Listeners
    fun getChild(plantID: String): DatabaseReference
    {
        return plants.child(plantID)
    }

    companion object {
        // Singleton to prevent multiple instances from existing
        private var INSTANCE: FirebaseRepository? = null

        fun getInstance(): FirebaseRepository {
            if (INSTANCE == null) {
                INSTANCE = FirebaseRepository()
            }
            return INSTANCE!!
        }
    }
}