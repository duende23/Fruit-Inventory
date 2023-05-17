package com.villadevs.fruitinventory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.villadevs.fruitinventory.data.FruitDao

class FruitViewModelFactory (private val fruitDao: FruitDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FruitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FruitViewModel(fruitDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}