package com.example.cloop.presentation.donate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cloop.data.repository.DonateRepository

class DonateViewModelFactory(
    private val repository: DonateRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DonateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DonateViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}