package com.example.waterapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.waterapp.R
import com.example.waterapp.models.ImageAdapter
import com.example.waterapp.viewmodels.HomeViewModel

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false) as ConstraintLayout
        textView = root.findViewById(R.id.textViewTest)
        textView2 = root.findViewById(R.id.text_home)

        mRecyclerView = root.findViewById(R.id.my_recycler_view)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = GridLayoutManager(this.context, 1)
        mRecyclerView.layoutManager = mLayoutManager
        mAdapter = ImageAdapter(this.requireContext())
        mRecyclerView.adapter = mAdapter

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.w("Testing this works", "testing")

        homeViewModel.getSelectedPersonalPlant().observe(viewLifecycleOwner, Observer { plant ->
            textView.text = plant.second.name
            mCurrentPosition = plant.first
        })

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView2.text = it
            Log.w("Testing this works", "damn")
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}