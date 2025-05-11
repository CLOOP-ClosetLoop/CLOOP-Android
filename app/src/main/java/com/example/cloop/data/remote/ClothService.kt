package com.example.cloop.data.remote

import com.example.cloop.data.model.cloth.ImageUploadResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ClothService {

    @Multipart
    @POST("clothes/image")
    suspend fun uploadClothImage(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): Response<ImageUploadResponse>

}