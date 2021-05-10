package com.example.waterapp.views

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.waterapp.R
import com.example.waterapp.database.PersonalPlant
import com.example.waterapp.models.PlantAdapter
import com.example.waterapp.repositories.PersonalPlantRepository
import com.example.waterapp.viewmodels.HomeViewModel
import com.example.waterapp.viewmodels.TipViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var root: ConstraintLayout
    private lateinit var personalPlantList: List<PersonalPlant>

    //Tip of the Day
    private val tipViewModel: TipViewModel by activityViewModels()
    private lateinit var tipView: TextView
    private lateinit var nextTip: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnDelete: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.home_fragment, container, false) as ConstraintLayout
        recyclerView = root.findViewById<RecyclerView>(R.id.plantRecyclerView)

        GlobalScope.launch {
            // Initialize data. Getting the personal plants from database
            personalPlantList = PersonalPlantRepository.getInstance().getAllPlants()
            // Adding them to recyclerView
            recyclerView.adapter = PlantAdapter(this, personalPlantList)

            // Use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            recyclerView.setHasFixedSize(true)
        }

        //Delete Button
        btnDelete = root.findViewById<Button>(R.id.btnDelete)

        //Tip of the day
        tipView = root.findViewById(R.id.tipView)
        tipViewModel.randomTip()
        nextTip = root.findViewById(R.id.nextTip)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe the Tip LiveData and update the view on change
        tipViewModel.tip.observe(viewLifecycleOwner) { tip ->
            tipView.text = tip.text
        }

        nextTip.setOnClickListener { tipViewModel.randomTip() }

        btnDelete.setOnClickListener{delete()}
    }

    private fun delete(){
        val plantNames = ArrayList<String?>()
        //val plantNames = Array(recyclerView.adapter!!.itemCount) { i -> i * 1 }
        for(i in 0 until recyclerView.adapter!!.itemCount){
            val plantName: String? = personalPlantList[i].personalName
            plantNames.add(plantName)
        }
        val items = plantNames.toTypedArray()
        val selectedList = ArrayList<Int>()
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Deleting plants")
        builder.setMultiChoiceItems(items, null
        ) { _, which, isChecked ->
            if (isChecked) {
                selectedList.add(which)
            } else if (selectedList.contains(which)) {
                selectedList.remove(Integer.valueOf(which))
            }
        }

        builder.setPositiveButton("Delete") { dialogInterface, i ->
            val selectedStrings = ArrayList<String?>()

            for (j in selectedList.indices) {
                selectedStrings.add(items[selectedList[j]])
            }

            Toast.makeText(context, "Items selected are: " + selectedStrings.toTypedArray()
                .contentToString(), Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancel") { _, _ ->
            Toast.makeText(context,
                "Action was Cancelled", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }
}