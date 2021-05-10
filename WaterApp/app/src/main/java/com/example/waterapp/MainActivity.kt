package com.example.waterapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.waterapp.database.AppDatabase
import com.example.waterapp.database.PersonalPlant
import com.example.waterapp.repositories.FirebaseRepository
import com.example.waterapp.repositories.PersonalPlantRepository
import com.example.waterapp.repositories.PlantRepository
import com.example.waterapp.viewmodels.AddNewViewModel
import com.example.waterapp.viewmodels.PlantViewModel
import com.example.waterapp.views.HomeFragment
import com.example.waterapp.views.SearchFragment
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var plantViewModel: PlantViewModel
    private lateinit var newViewModel: AddNewViewModel
    private lateinit var personalPlantsList: List<PersonalPlant>
    private lateinit var editText: EditText
    private lateinit var plantName: String
    private lateinit var plantType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var context = this;
        plantViewModel = ViewModelProvider(this).get(PlantViewModel::class.java)

        GlobalScope.launch {
            AppDatabase.getAppDatabase(context)!!
            personalPlantsList = PersonalPlantRepository.getInstance().getAllPlants()
        }

        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment, HomeFragment()) //begin on homefragment
                .commit()
        }

        val btnHome = findViewById<Button>(R.id.btnHome)
        btnHome.setOnClickListener { updateFragment(HomeFragment()) }

        val btnSearch = findViewById<Button>(R.id.btnSearch)
        btnSearch.setOnClickListener { updateFragment(SearchFragment()) }

        val btnNew = findViewById<Button>(R.id.btnNew)
        btnNew.setOnClickListener {
            if (!plantViewModel.getPlantIsSet()) {
                updateFragment(SearchFragment())
            } else {
                addNewPlant()
            }
        }
    }

    private fun updateFragment(currentFragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment, currentFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun addNewPlant() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        plantType = plantViewModel.getSelectedPlant().value!!.name
        plantName = plantType
        lateinit var potGroup: RadioGroup
        lateinit var plantGroup: RadioGroup
        builder.setTitle("Adding $plantType")
        val firebase = FirebaseRepository.getInstance()

        val dialogLayout = inflater.inflate(R.layout.add_new_plant, null)
        editText = dialogLayout.findViewById(R.id.personalName)
        builder.setView(dialogLayout)

        potGroup = dialogLayout.findViewById(R.id.potGroup)
        plantGroup = dialogLayout.findViewById(R.id.plantGroup)
        newViewModel = ViewModelProvider(this).get(AddNewViewModel::class.java)

        potGroup.setOnCheckedChangeListener { potGroup, id ->
            val rb = potGroup.findViewById(id) as RadioButton
            newViewModel.setPotSize(rb.text.toString())
        }

        plantGroup.setOnCheckedChangeListener { plantGroup, id ->
            val rb = plantGroup.findViewById(id) as RadioButton
            newViewModel.setPlantSize(rb.text.toString())
        }

        builder.setPositiveButton("Add $plantType") { _, _ ->

            GlobalScope.launch {
                if (editText.text.toString().trim().isNotEmpty()) {
                    plantName = editText.text.toString()
                }
                if (validateName()) {
                    newViewModel.setName(plantName, plantType)
                    firebase.incrementPlant(plantType)
                    newViewModel.createNewPersonalPlant()
                } else {
                    //editText.error = resources.getString(R.string.nameError)
                }
            }
                Toast.makeText(applicationContext,"$plantType added ", Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("Cancel") { _, _ ->
                Toast.makeText( applicationContext,"Action was Cancelled", Toast.LENGTH_SHORT).show()
            }
            builder.show()

    }


    private fun validateName(): Boolean {
        if (plantName != plantType) {
            return PersonalPlantRepository.getInstance().checkPersonalName(plantName)
        } else {
            if (PersonalPlantRepository.getInstance().getAllPlantsOfSameType(plantType).isNotEmpty()){
                plantName += PersonalPlantRepository.getInstance().getAllPlantsOfSameType(plantType).size
                return true
            }
        }
        return false
    }
}
