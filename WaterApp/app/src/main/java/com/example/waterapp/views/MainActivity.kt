package com.example.waterapp.views

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.waterapp.R
import com.example.waterapp.database.AppDatabase
import com.example.waterapp.database.PersonalPlant
import com.example.waterapp.database.PersonalPlantDao
import com.example.waterapp.database.Plant
import com.example.waterapp.models.PlantManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db: AppDatabase = AppDatabase.getAppDatabase(this)!!

        val personalPlantDao: PersonalPlantDao = db.personalPlantDao()
        val plantDao = db.plantDao()

        //Nuketable removes everything from the table
        plantDao.nukeTable()
        //Insert takes a plant in inserts it. Get basic plant just creates a template plant
        plantDao.insert(Plant.getBasicPlant()!!)

        //See above
        personalPlantDao.nukeTable()
        personalPlantDao.insert(PersonalPlant.getBasicPlant()!!)

        //Write the cout of each database (Should be 1 in each as we first remove everything and then add 1 new)
        Log.w("Plant Count", "Personal plant count: " + personalPlantDao.countPlants().toString())
        Log.w("Plant Count", "Abstract Plant count: " + personalPlantDao.countPlants().toString())

        val rootNode: FirebaseDatabase = FirebaseDatabase.getInstance()
        val plants = rootNode.getReference("Plants")

        //Creates a new plant with id fa405ab3-8b31-45ef-a15a-c8d74bfb7h45 and value 6
        //If id already exist it will just update the value
        plants.child("fa405ab3-8b31-45ef-a15a-c8d74bfb7h45").setValue(6)

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value

                Log.w("DataChange", value.toString())

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("DataChangeError", error.details)
            }

        }
        //Checks if the quey was succesful and if it was print the value to logcat
        val completeListener = object : OnCompleteListener<DataSnapshot>{
            override fun onComplete(p0: Task<DataSnapshot>) {
                Log.w("Tag", "onComplete " + p0.isSuccessful)
                if (p0.isSuccessful)
                {
                    Log.w("Plant Count", p0.result.toString())
                }
            }
        }
        //Ads a value event listner that runs code every time any data on the firebase changes (Will return the enitre database)
        plants.addValueEventListener(valueEventListener)

        //Ads a value even listner that runs code every time the data for that child is changed (Will only return the new number in our case)
        plants.child("fa405ab3-8b31-45ef-a15a-c8d74bfb5b45").addValueEventListener(valueEventListener)

        //Gets the child with ID fa405ab3-8b31-45ef-a15a-c8d74bfb5b45 from the database on does "something" to it (As specified in the complete listener)
        plants.child("fa405ab3-8b31-45ef-a15a-c8d74bfb5b45").get().addOnCompleteListener(completeListener)

        //set starting view
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_information, R.id.nav_search), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}