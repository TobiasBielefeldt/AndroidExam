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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.waterapp.R
import com.example.waterapp.models.ImageAdapter
import com.example.waterapp.viewmodels.HomeViewModel
import com.example.waterapp.viewmodels.TipViewModel


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private var mCurrentPosition = -1
    private lateinit var root: ConstraintLayout
    private lateinit var textView: TextView
    private lateinit var textView2: TextView

    //image handler
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: RecyclerView.Adapter<*>
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    //Tip of the Day
    private val tipViewModel: TipViewModel by activityViewModels()
    private lateinit var tipView: TextView
    private lateinit var nextTip: TextView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false) as ConstraintLayout
        textView = root.findViewById(R.id.textViewTest)

        mRecyclerView = root.findViewById(R.id.my_recycler_view)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = GridLayoutManager(this.context, 1)
        mRecyclerView.layoutManager = mLayoutManager
        mAdapter = ImageAdapter(this.requireContext())
        mRecyclerView.adapter = mAdapter

        //Tip of the day
        tipView = root.findViewById(R.id.tipView)
        tipViewModel.randomTip()
        nextTip = root.findViewById(R.id.nextTip)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getSelectedPersonalPlant().observe(viewLifecycleOwner, Observer { plant ->
            textView.text = plant.second.name
            mCurrentPosition = plant.first
        })

        // Observe the Tip LiveData and update the view on change
        tipViewModel.tip.observe(viewLifecycleOwner) { tip ->
            tipView.text = tip.text
        }

        nextTip.setOnClickListener { tipViewModel.randomTip() }

    }

}