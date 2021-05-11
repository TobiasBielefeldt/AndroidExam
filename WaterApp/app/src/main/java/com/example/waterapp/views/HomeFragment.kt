package com.example.waterapp.views

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.waterapp.R
import com.example.waterapp.database.PersonalPlant
import com.example.waterapp.models.PlantAdapter
import com.example.waterapp.repositories.FirebaseRepository
import com.example.waterapp.repositories.PersonalPlantRepository
import com.example.waterapp.viewmodels.HomeViewModel
import com.example.waterapp.viewmodels.TipViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
    private lateinit var plantAdapter: PlantAdapter


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
            plantAdapter = PlantAdapter(personalPlantList.toMutableList())


            recyclerView.adapter = plantAdapter

            recyclerView.setHasFixedSize(false)
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
        tipViewModel.tip.observe(viewLifecycleOwner, Observer { tip ->
            tipView.text = tip.text
        })
        nextTip.setOnClickListener { tipViewModel.randomTip() }

        btnDelete.setOnClickListener{delete()}
    }

    private fun delete(){
        val plantNames = ArrayList<String?>()
        //val plantNames = Array(recyclerView.adapter!!.itemCount) { i -> i * 1 }
        for(i in 0 until personalPlantList.count()){
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

        var personalPlantRepository = PersonalPlantRepository.getInstance()
        var firebaseRepository = FirebaseRepository.getInstance()

        builder.setPositiveButton("Delete") { dialogInterface, i ->
            val selectedStrings = ArrayList<String?>()
            var plantRemoveOutput = ""
            for (j in selectedList.indices.reversed()) {
                selectedStrings.add(items[selectedList[j]])
                plantRemoveOutput += items[selectedList[j]] + ", "
                var index = items.indexOfFirst { it==items[selectedList[j]]}

                plantAdapter.removeStringItem(index)
            }

            var plantRemoveOutputReal = plantRemoveOutput.dropLast(2)

            for(personalName in selectedStrings) {
                GlobalScope.launch {
                    var personalPlant = personalPlantRepository.getPersonalPlantFromPersonalName(personalName!!)
                    firebaseRepository.decrementPlan(personalPlant.plantType!!)
                    personalPlantRepository.delete(personalPlant)
                    personalPlantList = PersonalPlantRepository.getInstance().getAllPlants()
                }
            }

            if(selectedList.isEmpty())
            {
                Toast.makeText(context, "Nothing Selected", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(context, "Plants deleted: $plantRemoveOutputReal", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancel") { _, _ ->
            Toast.makeText(context,
                "Action was Cancelled", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }
}