package com.example.waterapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.waterapp.database.AppDatabase
import com.example.waterapp.database.PersonalPlant
import com.example.waterapp.repositories.FirebaseRepository
import com.example.waterapp.repositories.PersonalPlantRepository
import com.example.waterapp.repositories.PlantRepository
import com.example.waterapp.views.HomeFragment
import com.example.waterapp.views.InformationFragment
import com.example.waterapp.views.SearchFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var context = this;
        //This should run ones when the app starts and never again (Properly)


        //Call singleton to get an instance


        //Database stuff should be done in a Coroutine
        GlobalScope.launch{
            val db: AppDatabase = AppDatabase.getAppDatabase(context)!!
            val personalPlantRepository = PersonalPlantRepository.getInstance()
            val plantRepository = PlantRepository.getInstance()
            //Nuketable removes everything from the table
            //Insert takes a plant in inserts it. Get basic plant just creates a template plant
            personalPlantRepository.nukeTable()
            personalPlantRepository.insert(PersonalPlant.getBasicPlant()!!)

            //Write the cout of each database (Should be 1 in each as we first remove everything and then add 1 new)
            Log.w("Plant Count", "Personal plant count: " + personalPlantRepository.count().toString())
            Log.w("Plant Count", "Abstract Plant count: " + plantRepository.count().toString())
        }

        val firebase = FirebaseRepository.getInstance()
        //I don't think firebase has to be done in a corotoune since they use listners which wait for stuff to happen

        //Increment or decrement plants with that id
        //firebase.decrementPlan("fa405ab3-8b31-45ef-a15a-c8d74bfb7h45")
        //firebase.incrementPlant("fa405ab3-8b31-45ef-a15a-c8d74bfb5b45")

        /*
        This creates an even listner that activates when something changes on the database, it runs once before listening aswell
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Write code here (This prints as an example)
                val value = snapshot.value
                Log.w("DataChange", value.toString())
            }
            override fun onCancelled(error: DatabaseError) { }
        }
        //Adds the valueEventListner to a plant with the id
        firebase.getChild("fa405ab3-8b31-45ef-aaaa-c8d74bfb7h45").addValueEventListener(valueEventListener)

        */

        setContentView(R.layout.activity_main)
        //We start on the home fragment
        updateFragment(HomeFragment())

        val btnHome = findViewById<Button>(R.id.btnHome)
        btnHome.setOnClickListener{updateFragment(HomeFragment())}

        val btnSearch = findViewById<Button>(R.id.btnSearch)
        btnSearch.setOnClickListener{updateFragment(SearchFragment())}

    }
    private fun updateFragment(currentFragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment, currentFragment)
            .addToBackStack(null)
            .commit()
    }
}