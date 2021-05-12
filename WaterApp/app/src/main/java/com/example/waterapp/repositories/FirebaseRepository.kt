package com.example.waterapp.repositories

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseRepository {
    //The firebase databse works in nodes
    //
    private val rootNode: FirebaseDatabase = FirebaseDatabase.getInstance()

    //The plant node is where all the plants + data is
    //We did this to make sure we could create more on the root node if it was needed
    private val plants = rootNode.getReference("Plants")

    //Updates the value with the plantName or creates a new one of the name does not exist
    fun insertOrUpdate(plantName: String, number: Int)
    {
        plants.child(plantName).setValue(number)
    }

    //Takes the string and gets the current value then increments it by 1 (It does this with a listener, so a thread is not needed)
    fun incrementPlant(plantName: String, incrementAmount: Int)
    {
        val completeListener = object : OnCompleteListener<DataSnapshot> {
            override fun onComplete(p0: Task<DataSnapshot>) {
                if (p0.isSuccessful)
                {
                    insertOrUpdate(plantName, p0.result?.value.toString().toInt() + incrementAmount)
                }
            }
        }
        plants.child(plantName).get().addOnCompleteListener(completeListener)
    }

    //Same as increment just decrement
    fun decrementPlan(plantName: String, decrementAmount: Int)
    {
        val completeListener = object : OnCompleteListener<DataSnapshot> {
            override fun onComplete(p0: Task<DataSnapshot>) {
                if (p0.isSuccessful)
                {
                    insertOrUpdate(plantName, p0.result?.value.toString().toInt() - decrementAmount)
                }
            }
        }
        plants.child(plantName).get().addOnCompleteListener(completeListener)
    }

    //Retuns a databaseReference of a plant child
    //This is used to create your own onComplete/Failure/Change Listeners
    fun getChild(plantName: String): DatabaseReference
    {
        return plants.child(plantName)
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