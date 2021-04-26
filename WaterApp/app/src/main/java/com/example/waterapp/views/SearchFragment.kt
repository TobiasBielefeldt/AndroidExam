package com.example.waterapp.views

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.waterapp.R
import com.example.waterapp.viewmodels.PlantViewModel
import com.example.waterapp.viewmodels.SearchViewModel
import com.google.android.gms.dynamic.SupportFragmentWrapper

class SearchFragment : Fragment() {

    private val plantViewModel: PlantViewModel by activityViewModels()
    private lateinit var listView: ListView
    private lateinit var searchView: SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.search_fragment, container, false)
        listView = root.findViewById(R.id.listSearchView)
        searchView = root.findViewById(R.id.searchView)

        //Setting the listView to getPlantNames()
        var adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1, plantViewModel.getPlantNames())
        listView.adapter = adapter
        //Set listener on listView checking if items are clicked
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            plantViewModel.selectPlantAt(position)
            // Do something in response to the click
        }

        //Setting listener on searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            //Performs search when user hit the search button on the keyboard
            override fun onQueryTextSubmit(searchedWord: String?): Boolean {
                //Not used here, since text changes is used instead
                return false
            }

            //Start filtering the list as user start entering the characters
            override fun onQueryTextChange(searchedWord: String?): Boolean {
                adapter.filter.filter(searchedWord)
                return false
            }
        })

            return root
        }
    
}
