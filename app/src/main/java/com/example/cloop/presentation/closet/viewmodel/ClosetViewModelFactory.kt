package com.example.cloop.presentation.closet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cloop.data.remote.ClothService

class ClosetViewModelFactory(private val clothService: ClothService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClosetViewModel::class.java)) {
            return ClosetViewModel(clothService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

