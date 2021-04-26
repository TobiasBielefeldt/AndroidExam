package com.example.waterapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.waterapp.R
import com.example.waterapp.viewmodels.InformationViewModel
import com.example.waterapp.viewmodels.PlantViewModel

class InformationFragment : Fragment() {

    private val informationViewModel: InformationViewModel by activityViewModels()
    private val plantViewModel: PlantViewModel by activityViewModels()
    private var mCurrentPosition = -1
    private lateinit var root: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        root = inflater.inflate(R.layout.fragment_information, container, false) as TextView
        val textView: TextView = root.findViewById(R.id.text_information)
        informationViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        plantViewModel.getSelectedPlant().observe(viewLifecycleOwner) {
            root.text = it.second.name
            mCurrentPosition = it.first
        }
    }
}
