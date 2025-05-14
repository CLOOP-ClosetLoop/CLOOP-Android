package com.example.cloop.presentation.closet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloop.TokenManager
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
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }


    fun classifyClothWithAI(token: String, onResult: (Boolean) -> Unit) {
        val imageUrl = imageUrl ?: return onResult(false)
        val bearerToken = "Bearer $token"

        viewModelScope.launch {
            try {
                val response = RetrofitClient.aiService.classifyCloth(
                    bearerToken,
                    mapOf("imageUrl" to imageUrl)
                )
                if (response.isSuccessful) {
                    val ai = response.body() ?: return@launch onResult(false)
                    clothName = ai.predictedName
                    category = ai.predictedCategory
                    color = ai.predictedColor
                    season = ai.predictedSeason
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }


}
