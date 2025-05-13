package com.example.cloop.presentation.donate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloop.data.model.donate.DonationCloth
import com.example.cloop.data.repository.DonateRepository
import kotlinx.coroutines.launch

class DonateViewModel(private val repository: DonateRepository) : ViewModel() {

    private val _donationClothes = MutableLiveData<List<DonationCloth>>()
    val donationClothes: LiveData<List<DonationCloth>> = _donationClothes

    fun fetchDonationClothes(token: String) {
        viewModelScope.launch {
            val result = repository.getDonationCandidates(token)
            _donationClothes.value = result ?: emptyList()
        }
    }


    fun confirmDonation(token: String, clothId: Int, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.confirmDonation(token, clothId)
            onComplete(success)
        }
    }


    fun removeClothItem(clothId: Int) {
        _donationClothes.value = _donationClothes.value?.filter { it.clothId != clothId }
    }
}