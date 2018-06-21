package com.bortolan.iquadriv2.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val selectedItem = MutableLiveData<Int>()

    fun init() {
        TODO("Fetch default screen from preferences")
    }
}