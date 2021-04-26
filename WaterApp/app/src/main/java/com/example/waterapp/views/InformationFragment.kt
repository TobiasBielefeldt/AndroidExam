package com.example.waterapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.example.waterapp.R
import com.example.waterapp.viewmodels.InformationViewModel
import com.example.waterapp.viewmodels.PlantViewModel

class InformationFragment : Fragment() {

    private val informationViewModel: InformationViewModel by activityViewModels()
    private val plantViewModel: PlantViewModel by activityViewModels()
    private var mCurrentPosition = -1
    private lateinit var root: ConstraintLayout


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        root = inflater.inflate(R.layout.information_fragment, container, false) as ConstraintLayout
        val textView: TextView = root.findViewById(R.id.text_information)
        informationViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val plantName: TextView = root.findViewById(R.id.plantName)

        plantViewModel.getSelectedPlant().observe(viewLifecycleOwner) {
            plantName.text = it.second.name
            mCurrentPosition = it.first
        }
    }
}
