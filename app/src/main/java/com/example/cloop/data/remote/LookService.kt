package com.example.cloop.data.remote

import com.example.cloop.data.model.look.LookItem
import com.example.cloop.data.model.look.LookRegisterResponse
import com.example.cloop.data.model.look.LookRequest
import com.example.cloop.data.model.look.UploadResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface LookService {

    @Multipart
    @POST("/looks/image")
    suspend fun uploadLookImage(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): Response<UploadResponse>


    @POST("/looks")
    suspend fun registerLook(
        @Header("Authorization") token: String,
        @Body lookData: LookRequest
    ): Response<LookRegisterResponse>


    @GET("looks")
    suspend fun getLooksByDate(
        @Header("Authorization") token: String,
        @Query("date") date: String
    ): Response<List<LookItem>>
}
