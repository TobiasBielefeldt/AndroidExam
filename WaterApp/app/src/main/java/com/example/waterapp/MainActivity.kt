package com.example.waterapp

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.waterapp.constants.Constants
import com.example.waterapp.database.AppDatabase
import com.example.waterapp.database.PersonalPlant
import com.example.waterapp.database.Plant
import com.example.waterapp.repositories.PersonalPlantRepository
import com.example.waterapp.viewmodels.PlantViewModel
import com.example.waterapp.views.HomeFragment
import com.example.waterapp.views.SearchFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.Serializable
import java.io.SerializablePermission
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var plantViewModel: PlantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var context = this;
        plantViewModel = ViewModelProvider(this).get(PlantViewModel::class.java)

        //Initialises the database
        GlobalScope.launch {
            AppDatabase.getAppDatabase(context)!!
        }

        setContentView(R.layout.activity_main)

        //If we do not have a saved instance then set the mainfragment to homefragment
        //If we do have a saved instance then the mostly thing to have occurred is a screen rotation in which case we should not update the fragment
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment, HomeFragment()) //begin on homefragment
                .commit()
        }

        //Button initialization
        val btnHome = findViewById<Button>(R.id.btnHome)
        btnHome.setOnClickListener { updateFragment(HomeFragment()) }

        val btnSearch = findViewById<Button>(R.id.btnSearch)
        btnSearch.setOnClickListener{updateFragment(SearchFragment())}
    }

    //Simple fragment update function
    private fun updateFragment(currentFragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment, currentFragment)
                .addToBackStack(null)
                .commit()
    }
}
