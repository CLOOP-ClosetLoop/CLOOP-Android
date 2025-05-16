package com.example.cloop.presentation.home.viewmodel

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloop.TokenManager
import com.example.cloop.data.model.cloth.Cloth
import com.example.cloop.data.model.look.LookRegisterResponse
import com.example.cloop.data.model.look.LookRequest
import com.example.cloop.data.remote.RetrofitClient
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class OutfitRegisterViewModel : ViewModel() {

    val selectedClothList = MutableLiveData<List<Cloth>>(emptyList())
    val imageUrl = MutableLiveData<String?>()
    val registerResult = MutableLiveData<LookRegisterResponse?>()

    private val _allClothes = MutableLiveData<List<Cloth>>()
    val allClothes: LiveData<List<Cloth>> = _allClothes

    fun setAllClothes(clothes: List<Cloth>) {
        _allClothes.value = clothes
    }

    fun isClothSelected(clothId: Int): Boolean {
        return selectedClothList.value?.any { it.clothId == clothId } ?: false
    }

    fun toggleClothSelection(cloth: Cloth) {
        val currentList = selectedClothList.value?.toMutableList() ?: mutableListOf()
        if (currentList.any { it.clothId == cloth.clothId }) {
            currentList.removeAll { it.clothId == cloth.clothId }
        } else {
            currentList.add(cloth)
        }
        selectedClothList.value = currentList
    }

    fun setImageUri(uri: Uri) {
        imageUrl.value = uri.toString()
    }

    fun registerLook(context: Context, clothIds: List<Int>, wornDate: String) {
        val uriString = imageUrl.value ?: return
        val uri = Uri.parse(uriString)
        uploadImageAndRegisterLook(context, uri, clothIds, wornDate)
    }

    fun clearRegisterData() {
        selectedClothList.value = emptyList()
        imageUrl.value = null
        registerResult.value = null
    }

    private fun uploadImageAndRegisterLook(context: Context, uri: Uri, clothIds: List<Int>, wornDate: String) {
        viewModelScope.launch {
            try {
                val token = TokenManager.getAccessToken(context)?.let { "Bearer $it" } ?: return@launch

                val file = File(getRealPathFromUri(context, uri))
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val multipart = MultipartBody.Part.createFormData("image", file.name, requestFile)

                val uploadResponse = RetrofitClient.lookService.uploadLookImage(token, multipart)
                if (uploadResponse.isSuccessful) {
                    val uploadedUrl = uploadResponse.body()?.imageUrl ?: return@launch
                    imageUrl.value = uploadedUrl

                    val lookRequest = LookRequest(imageUrl = uploadedUrl, clothIds = clothIds, wornDate = wornDate)
                    val registerResponse = RetrofitClient.lookService.registerLook(token, lookRequest)

                    if (registerResponse.isSuccessful) {
                        registerResult.value = registerResponse.body()
                    }
                }
            } catch (e: Exception) {
                Log.e("RegisterLook", "Error: ${e.localizedMessage}")
            }
        }
    }

    private fun getRealPathFromUri(context: Context, uri: Uri): String {
        var path = ""
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            path = cursor.getString(columnIndex)
        }
        return path
    }
}

