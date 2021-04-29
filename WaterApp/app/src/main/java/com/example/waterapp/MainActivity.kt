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

        GlobalScope.launch{
            val db: AppDatabase = AppDatabase.getAppDatabase(context)!!
        }

        val firebase = FirebaseRepository.getInstance()

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
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment, HomeFragment()) //begin on homefragment
                .commit()
        }

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