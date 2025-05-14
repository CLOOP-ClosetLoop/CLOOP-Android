package com.example.cloop.data.remote

import com.example.cloop.data.model.cloth.Cloth
import com.example.cloop.data.model.cloth.ClothRegisterRequest
import com.example.cloop.data.model.cloth.ImageUploadResponse
import com.example.cloop.data.model.cloth.RegisterClothResponse
import com.example.cloop.data.model.cloth.WearStat
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
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


    @POST("clothes")
    suspend fun registerCloth(
        @Header("Authorization") token: String,
        @Body request: ClothRegisterRequest
    ): Response<RegisterClothResponse>


    @GET("/clothes")
    suspend fun getClothes(
        @Header("Authorization") accessToken: String
    ): Response<List<Cloth>>


    @GET("/clothes/statistics")
    suspend fun getWearStats(
        @Header("Authorization") accessToken: String
    ): Response<List<WearStat>>

}