package com.example.waterapp.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.waterapp.R
import com.example.waterapp.database.PersonalPlant
import com.example.waterapp.helper.ImageHelper
import com.example.waterapp.repositories.FirebaseRepository
import com.example.waterapp.repositories.PersonalPlantRepository
import com.example.waterapp.viewmodels.AddNewViewModel
import com.example.waterapp.viewmodels.PlantViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InformationFragment : Fragment() {

    private val plantViewModel: PlantViewModel by activityViewModels()
    private lateinit var root: ConstraintLayout
    private lateinit var imageView: ImageView
    private lateinit var nameView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var sunView: TextView
    private lateinit var waterView: TextView
    private lateinit var userView: TextView
    private lateinit var waterIcons: LinearLayout
    private lateinit var sunIcons: LinearLayout
    private lateinit var editText: EditText
    private lateinit var newViewModel: AddNewViewModel
    private lateinit var plantName: String
    private lateinit var plantType: String
    private var personalPlantListName: MutableList<String> = emptyArray<String>().toMutableList()
    private var userViewStringSucces: String? = null
    private var userViewStringError: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        root = inflater.inflate(R.layout.information_fragment, container, false) as ConstraintLayout
        imageView = root.findViewById(R.id.imageView)
        nameView = root.findViewById(R.id.plantName)
        descriptionView = root.findViewById(R.id.plantDescription)
        sunView = root.findViewById(R.id.sunNeed)
        sunIcons = root.findViewById(R.id.sunIcons)
        waterView = root.findViewById(R.id.waterNeed)
        waterIcons = root.findViewById(R.id.waterIcons)
        userView = root.findViewById(R.id.userScore)

        userViewStringSucces = resources.getString(R.string.databaseSucces)
        userViewStringError = resources.getString(R.string.databaseError)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val firebase = FirebaseRepository.getInstance()
        val valueEventListener = object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                //Write code here (This prints as an example)
                userView.text = userViewStringSucces + " " + snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                userView.text = userViewStringError
            }
        }
        //Adds the valueEventListner to a plant with the id
        firebase.getChild(plantViewModel.getSelectedPlant().value!!.name)
            .addValueEventListener(valueEventListener)

        GlobalScope.launch {
            var personalPlantList = PersonalPlantRepository.getInstance().getAllPlants()
            for(personalPlant in personalPlantList)
            {
                personalPlantListName.add(personalPlant.personalName!!)
            }

        }

        var plant = plantViewModel.getSelectedPlant()

            Glide.with(requireContext())
                .clear(imageView)
            Glide.with(requireContext())
                .load(ImageHelper.getImage(plant.value!!.name))
                .apply(RequestOptions().circleCrop())
                .into(imageView)
            nameView.text = plant.value!!.name


            descriptionView.text = plant.value!!.description
            sunView.text = plantViewModel.getSunDescription(plant.value!!.sunNeed)
            repeat(plant.value!!.sunNeed) {
                var imageView = ImageView(this.requireContext())
                Glide.with(requireContext())
                    .load(R.drawable.sun)
                        .apply(RequestOptions().override(70,70))
                    .into(imageView)
                sunIcons.addView(imageView)
            }
            val waterPair: Pair<Int, String> = plantViewModel.transformWaterNeed(plant.value!!.waterNeed)
            waterView.text = waterPair.second
            repeat(waterPair.first) {
                var imageView = ImageView(this.requireContext())
                Glide.with(requireContext())
                    .load(R.drawable.water_drop)
                        .apply(RequestOptions().override(70,70))
                    .into(imageView)
                waterIcons.addView(imageView)
            }


        val btnNew = root.findViewById<Button>(R.id.btnNew)
        btnNew.setOnClickListener {
            addNewPlant()
        }
    }


    private fun addNewPlant() {
        val builder = AlertDialog.Builder(requireContext())
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
            if(editText.text.toString().trim().isNotEmpty()) {
                plantName = editText.text.toString()
            } else {
                plantName = plantType
            }

            while (personalPlantListName.contains(plantName))
            {
                plantName = "New $plantName"
            }

            newViewModel.setName(plantName, plantType)
            GlobalScope.launch {
                firebase.incrementPlant(plantType,1)
                newViewModel.createNewPersonalPlant()
                var personalPlantList = PersonalPlantRepository.getInstance().getAllPlants()
                personalPlantListName.clear()
                for(personalPlant in personalPlantList)
                {
                    personalPlantListName.add(personalPlant.personalName!!)
                }
            }

            Toast.makeText(context, "$plantName added ", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(context, "Action was Cancelled", Toast.LENGTH_SHORT).show()
        }

        builder.show()
        }
    }