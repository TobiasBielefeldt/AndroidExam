package com.example.waterapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    private val _list = MutableLiveData<ArrayList<String>>().apply {
        val value =
                listOf("Lahore", "Berlin", "Lisbon", "Tokyo", "Toronto", "Sydney", "Osaka", "Istanbul")
    }
    val list: LiveData<ArrayList<String>> = _list
}