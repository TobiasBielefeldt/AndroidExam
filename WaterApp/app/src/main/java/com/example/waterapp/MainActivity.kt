package com.example.waterapp

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
import com.example.waterapp.database.AppDatabase
import com.example.waterapp.database.PersonalPlant
import com.example.waterapp.database.Plant
import com.example.waterapp.repositories.FirebaseRepository
import com.example.waterapp.repositories.PersonalPlantRepository
import com.example.waterapp.repositories.PlantRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //This needs to be called once at start up then never again (I think)
        val db = AppDatabase.getAppDatabase(this)!!

        //Call the singleton method to get an instance
        val personalPlantRepository = PersonalPlantRepository.getInstance()
        val plantRepository = PlantRepository.getInstance()

        //Calls to database should happend in a coroutine
        var i = GlobalScope.launch {
            //Nuketable removes everything from the table
            plantRepository.nukeTable()
            //Insert takes a plant in inserts it. Get basic plant just creates a template plant
            plantRepository.insert(Plant.getBasicPlant()!!)
            //See above
            personalPlantRepository.nukeTable()
            personalPlantRepository.insert(PersonalPlant.getBasicPlant()!!)
            personalPlantRepository.insert(PersonalPlant.getBasicPlant()!!)

            Log.w("Plant Count", "Personal plant count: " + personalPlantRepository.count().toString())
            Log.w("Plant Count", "Abstract Plant count: " + plantRepository.count().toString())
        }

        //Firebase does not need the coroutine since it all happens via liteners (They wait for something to happen but with out blocking).
        var firebase = FirebaseRepository.getInstance()

        firebase.incrementPlant("fa405ab3-8b31-45ef-a15a-c8d74bfb5b45")
        firebase.decrementPlan("fa405ab3-8b31-45ef-a15a-c8d74bfb7h45")

        /* Used to update when stuff changes, I also belive it runs once before (I don't think I can make this easy sadly)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Do stuff in here (As an example i just print it)
                val value = snapshot.value
                Log.w("DataChange", value.toString())
            }
            override fun onCancelled(error: DatabaseError) {   }
        }

        //This adds the listner to a child (Whoose id that is ofc)
        firebase.getChild("fa405ab3-8b31-45ef-a15a-c8d74bfb7h45").addValueEventListener(valueEventListener)
        */



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