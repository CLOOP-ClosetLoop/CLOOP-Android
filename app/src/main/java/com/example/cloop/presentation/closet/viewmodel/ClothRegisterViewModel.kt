package com.example.cloop.presentation.closet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloop.data.model.cloth.ClothRegisterRequest
import com.example.cloop.data.remote.RetrofitClient
import kotlinx.coroutines.launch

class ClothRegisterViewModel : ViewModel() {
    var imageUrl: String? = null
    var category: String? = null
    var clothName: String? = null
    var purchasedAt: String? = null
    var brand: String? = null
    var color: String? = null
    var season: String? = null

    private val clothService = RetrofitClient.clothService

    fun registerCloth(token: String, onResult: (Boolean) -> Unit) {
        val request = ClothRegisterRequest(
            clothName = clothName ?: return,
            category = category ?: return,
            brand = brand ?: return,
            purchasedAt = purchasedAt ?: return,
            color = color ?: return,
            season = season ?: return,
            imageUrl = imageUrl ?: return
        )

        viewModelScope.launch {
            try {
                val response = clothService.registerCloth(token, request)
                if (response.isSuccessful) {
                    Log.d("ClothRegisterVM", "옷 등록 성공: ${response.body()}")
                    onResult(true)
                } else {
                    Log.e("ClothRegisterVM", "옷 등록 실패: ${response.code()}")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("ClothRegisterVM", "옷 등록 예외 발생: ${e.message}")
                onResult(false)
            }
        }
    }
}
