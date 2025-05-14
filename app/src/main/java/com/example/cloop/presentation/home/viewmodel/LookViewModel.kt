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

    private val _lookItems = MutableLiveData<List<LookItem>>()
    val lookItems: LiveData<List<LookItem>> get() = _lookItems

    fun fetchLooksByDate(context: Context, date: String) {
        val token = TokenManager.getAccessToken(context)

        if (token.isNullOrBlank()) {
            return
        }

        viewModelScope.launch {
            try {
                val response = lookService.getLooksByDate("Bearer $token", date)
                if (response.isSuccessful) {
                    val items = response.body().orEmpty()
                    _lookItems.value = items
                }
            } catch (e: Exception) {
            }
        }
    }
}
