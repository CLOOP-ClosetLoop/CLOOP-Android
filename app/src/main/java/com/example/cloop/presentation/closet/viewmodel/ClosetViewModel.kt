package com.example.cloop.presentation.closet.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloop.data.model.cloth.Cloth
import com.example.cloop.data.remote.ClothService
import kotlinx.coroutines.launch

class ClosetViewModel(private val clothService: ClothService) : ViewModel() {

    private val _allClothes = MutableLiveData<List<Cloth>>()
    val allClothes: LiveData<List<Cloth>> = _allClothes

    fun fetchClothes(token: String) {
        viewModelScope.launch {
            try {
                val response = clothService.getClothes("Bearer $token")
                if (response.isSuccessful) {
                    _allClothes.value = response.body() ?: emptyList()
                } else {
                    Log.e("ClosetViewModel", "Response failure: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ClosetViewModel", "API Error: ${e.localizedMessage}")
            }
        }
    }

    fun getClothesByCategory(category: String): List<Cloth> {
        return _allClothes.value?.filter {
            it.category.equals(category, ignoreCase = true)
        } ?: emptyList()
    }
}

