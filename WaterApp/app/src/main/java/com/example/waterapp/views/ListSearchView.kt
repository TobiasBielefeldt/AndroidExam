package com.example.waterapp.views

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
import androidx.fragment.app.activityViewModels
import com.example.waterapp.R
import com.example.waterapp.viewmodels.PlantViewModel

class ListSearchView: ListFragment() {
    private val plantViewModel: PlantViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // We need to use a different list item layout for devices older than Honeycomb
        val layout = android.R.layout.simple_list_item_activated_1

        // Create an array adapter for the list view, using the Ipsum headlines array
        listAdapter = ArrayAdapter(this.requireContext(), layout, plantViewModel.getPlantNames())
    }

    override fun onStart() {
        super.onStart()

        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at the point the listview is available.)
        if (parentFragmentManager.findFragmentById(R.id.listView) != null) {
            listView.choiceMode = ListView.CHOICE_MODE_SINGLE
        }
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        // Notify the parent activity of selected item
        //mCallback?.onArticleSelected(position)

        // Set the item as checked to be highlighted when in two-pane layout
        plantViewModel.selectPlantAt(position)
        listView.setItemChecked(position, true)
        //listView.setItemChecked(position, true)
    }
}