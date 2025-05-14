package com.example.cloop.presentation.closet.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloop.TokenManager
import com.example.cloop.data.model.cloth.WearStat
import com.example.cloop.data.remote.RetrofitClient
import kotlinx.coroutines.launch

class WearStatsViewModel(private val context: Context) : ViewModel() {

    private val _wearStats = MutableLiveData<List<WearStat>>()
    val wearStats: LiveData<List<WearStat>> = _wearStats

    fun fetchWearStats() {
        viewModelScope.launch {
            try {
                val token = TokenManager.getAccessToken(context) ?: return@launch
                val response = RetrofitClient.clothService.getWearStats("Bearer $token")

                if (response.isSuccessful) {
                    _wearStats.value = response.body()
                } else {
                    Log.e("WearStatsViewModel", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("WearStatsViewModel", "Exception: ${e.message}")
            }
        }
    }
}
