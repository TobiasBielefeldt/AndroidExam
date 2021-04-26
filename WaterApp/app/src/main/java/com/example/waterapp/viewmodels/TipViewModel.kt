package com.example.waterapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waterapp.models.Tip
import com.example.waterapp.repository.TipRepository

class TipViewModel : ViewModel() {

    private val tipRepository: TipRepository = TipRepository()
    private val _tip = MutableLiveData<Tip>()

    val tip: LiveData<Tip>
        get() = _tip

    fun randomTip() {
        _tip.value = tipRepository.fetchRandomTip()
    }
}