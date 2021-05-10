package com.example.waterapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.waterapp.database.AppDatabase
import com.example.waterapp.repositories.FirebaseRepository
import com.example.waterapp.repositories.PersonalPlantRepository
import com.example.waterapp.viewmodels.AddNewViewModel
import com.example.waterapp.viewmodels.PlantViewModel
import com.example.waterapp.views.HomeFragment
import com.example.waterapp.views.SearchFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var plantViewModel: PlantViewModel
    private lateinit var newViewModel: AddNewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var context = this;

        GlobalScope.launch{
            AppDatabase.getAppDatabase(context)!!
        }

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
