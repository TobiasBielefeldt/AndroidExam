package com.example.waterapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.waterapp.R
import com.example.waterapp.viewmodels.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
                ViewModelProvider(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        val listView: ListView = root.findViewById(R.id.listView)
        val searchView: SearchView = root.findViewById(R.id.searchView)

        val bestCities =
                listOf("Lahore", "Berlin", "Lisbon", "Tokyo", "Toronto", "Sydney", "Osaka", "Istanbul")
        var adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1, bestCities)
        listView.adapter = adapter
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                //Performs search when user hit the search button on the keyboard
//                if (bestCities.contains(p0)) {
//                    adapter.filter.filter(p0)
//                } else {
//                    Toast.makeText(this@MainActivity, "No match found", Toast.LENGTH_SHORT).show()
//                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                //Start filtering the list as user start entering the characters
                adapter.filter.filter(p0)
                return false
            }
        })

        return root
    }
}
