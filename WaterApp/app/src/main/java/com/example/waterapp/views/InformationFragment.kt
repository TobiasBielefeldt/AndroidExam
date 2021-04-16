package com.example.waterapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.waterapp.R
import com.example.waterapp.viewmodels.InformationViewModel

class InformationFragment : Fragment() {

    private lateinit var informationViewModel: InformationViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        informationViewModel =
                ViewModelProvider(this).get(InformationViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_information, container, false)
        val textView: TextView = root.findViewById(R.id.text_information)
        informationViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}