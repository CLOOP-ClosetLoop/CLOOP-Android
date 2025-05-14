package com.example.cloop.presentation.home.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloop.TokenManager
import com.example.cloop.data.model.look.LookItem
import com.example.cloop.data.remote.LookService
import kotlinx.coroutines.launch

class LookViewModel(private val lookService: LookService) : ViewModel() {

    private val _looks = MutableLiveData<List<LookItem>>()
    val looks: LiveData<List<LookItem>> get() = _looks

    fun fetchLooksByDate(context: Context, date: String) {
        val token = TokenManager.getAccessToken(context)
        if (token == null) {
            return
        }

        viewModelScope.launch {
            try {
                val response = lookService.getLooksByDate("Bearer $token", date)
                if (response.isSuccessful) {
                    _looks.value = response.body() ?: emptyList()
                } else {
                    Log.e("LookViewModel", "API Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("LookViewModel", "Exception: ${e.localizedMessage}")
            }
        }
    }
}
