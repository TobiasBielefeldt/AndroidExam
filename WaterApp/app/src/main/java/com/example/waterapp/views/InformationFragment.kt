package com.example.waterapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.waterapp.R
import com.example.waterapp.helper.ImageHelper
import com.example.waterapp.viewmodels.PlantViewModel

class InformationFragment : Fragment() {

    private val plantViewModel: PlantViewModel by activityViewModels()
    private lateinit var root: ConstraintLayout
    private lateinit var imageView: ImageView
    private lateinit var nameView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var sunView: TextView
    private lateinit var waterView: TextView
    private lateinit var waterIcons: LinearLayout
    private lateinit var sunIcons: LinearLayout


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        root = inflater.inflate(R.layout.information_fragment, container, false) as ConstraintLayout

        imageView = root.findViewById(R.id.imageView)
        nameView = root.findViewById(R.id.plantName)
        descriptionView = root.findViewById(R.id.plantDescription)
        sunView = root.findViewById(R.id.sunNeed)
        sunIcons = root.findViewById(R.id.sunIcons)
        waterView = root.findViewById(R.id.waterNeed)
        waterIcons = root.findViewById(R.id.waterIcons)

        return root
    }

    override fun onPause() {
        super.onPause()
        plantViewModel.deselectPlant()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        plantViewModel.getSelectedPlant().observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                    .clear(imageView)
            Glide.with(requireContext())
                    .load(ImageHelper.getImage(it.name))
                    .apply(RequestOptions().circleCrop())
                    .into(imageView)
            nameView.text = it.name
            descriptionView.text = it.description
            sunView.text = plantViewModel.getSunDescription(it.sunNeed)
            repeat(it.sunNeed) {
                var imageView = ImageView(this.requireContext())
                Glide.with(requireContext())
                        .load(R.drawable.sun)
                        .into(imageView)
                sunIcons.addView(imageView)
                imageView.layoutParams.height = 100
                imageView.layoutParams.width = 100
            }
            waterView.text = plantViewModel.getWaterDescription(it.waterNeed)
            repeat(it.waterNeed) {
                var imageView = ImageView(this.requireContext())
                Glide.with(requireContext())
                        .load(R.drawable.water_drop)
                        .into(imageView)
                waterIcons.addView(imageView)
                imageView.layoutParams.height = 100
                imageView.layoutParams.width = 100
            }
        }

    }
}
