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
import androidx.recyclerview.widget.RecyclerView
import com.example.waterapp.R
import com.example.waterapp.database.PersonalPlant
import com.example.waterapp.models.PlantAdapter
import com.example.waterapp.repositories.FirebaseRepository
import com.example.waterapp.repositories.PersonalPlantRepository
import com.example.waterapp.viewmodels.TipViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.home_fragment, container, false) as ConstraintLayout
        recyclerView = root.findViewById<RecyclerView>(R.id.plantRecyclerView)

        //Give an empty list
        plantAdapter = PlantAdapter(emptyArray<PersonalPlant>().toMutableList())
        recyclerView.adapter = plantAdapter
        recyclerView.setHasFixedSize(true)

        GlobalScope.launch {
            // Initialize data. Getting the personal plants from database
            personalPlantList = PersonalPlantRepository.getInstance().getAllPlants()
            //And then update the adapter when we have the data
            plantAdapter.updateItems(personalPlantList.toMutableList())
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
        //Gets all the names of the plants
        val plantNames = ArrayList<String?>()
        for(i in 0 until personalPlantList.count()){
            val plantName: String? = personalPlantList[i].personalName
            plantNames.add(plantName)
        }

        //Creates a multiplechoice box with the plantnames as items
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

        //If the positive button is pressed it will check if there are any selected and if there is remove them from the databases and from the Plantadapter
        builder.setPositiveButton("Delete") { dialogInterface, i ->
            if(selectedList.isEmpty())
            {
                Toast.makeText(context, "Nothing Selected", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val selectedStrings = ArrayList<String?>()
                var plantRemoveOutput = ""

                //The method we have on adapter takes an index
                //So we need to get a list of indexes to remove
                var indexes = mutableListOf<Int>()

                for (j in selectedList.indices) {
                    selectedStrings.add(items[selectedList[j]])
                    plantRemoveOutput += items[selectedList[j]] + ", "
                    indexes.add(items.indexOfFirst { it==items[selectedList[j]]})
                }

                var plantRemoveOutputReal = plantRemoveOutput.dropLast(2)


                //Update the databaseses, this will ofc be done inside a coroutine
                GlobalScope.launch {
                    updateAllDatabase(selectedStrings)
                }
                //We sort them to makes sure they are in order
                indexes.sort()
                //We do it revers to not get any errors
                //If we remove index 1,2 then 1 will move the dataset so index 2 will acuelly be premove index 3 which will either delete the wrong thing or give an error
                //But if we do it in revers and remove 2 first and then 1 everything is fine since removing 2 won't effect antyhing before it
                for(index in indexes.reversed())
                {
                    plantAdapter.removeItem(index)
                }

                //When we are done write this to the user
                Toast.makeText(context, "Plants deleted: $plantRemoveOutputReal", Toast.LENGTH_SHORT).show()
            }
        }

        //If the negative button was pressed just write this and do nothing else.
        builder.setNegativeButton("Cancel") { _, _ ->
            Toast.makeText(context,
                "Action was Cancelled", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }

    //Takes a list of personalPlantNames to remove and then removes them from the personalPlantDatabase and updates the firebase(By decremeting by the amount of plants deleted of that type)
    private fun updateAllDatabase(selectedStrings: ArrayList<String?>) {
        var decrementPlant = mutableMapOf<String, Int>()
        var personalPlantRepository = PersonalPlantRepository.getInstance()
        var firebaseRepository = FirebaseRepository.getInstance()

        for (personalName in selectedStrings) {
            var personalPlant = personalPlantRepository.getPersonalPlantFromPersonalName(personalName!!)
            personalPlantRepository.delete(personalPlant)
            personalPlantList = PersonalPlantRepository.getInstance().getAllPlants()

            if (decrementPlant.containsKey(personalPlant.plantType)) {
                var i = decrementPlant[personalPlant.plantType]!!
                i++
                decrementPlant[personalPlant.plantType!!] = i
            } else {
                decrementPlant.put(personalPlant.plantType!!, 1)
            }
        }
        for (key in decrementPlant.keys) {
            firebaseRepository.decrementPlan(key, decrementPlant[key]!!)
        }
    }
}