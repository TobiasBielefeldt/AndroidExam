package com.example.waterapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var root: ConstraintLayout
    private lateinit var personalPlantList: List<PersonalPlant>

    //Tip of the Day
    private val tipViewModel: TipViewModel by activityViewModels()
    private lateinit var tipView: TextView
    private lateinit var nextTip: TextView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.home_fragment, container, false) as ConstraintLayout

        GlobalScope.launch {
            // Initialize data. Getting the personal plants from database
            personalPlantList = PersonalPlantRepository.getInstance().getAllPlants()
            // Adding them to recyclerView
            val recyclerView = root.findViewById<RecyclerView>(R.id.plantRecyclerView)
            recyclerView.adapter = PlantAdapter(this, personalPlantList)

            // Use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            recyclerView.setHasFixedSize(true)
        }

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

    }
}