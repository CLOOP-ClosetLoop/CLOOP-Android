package com.example.cloop.data.remote

import com.example.cloop.data.model.cloth.AiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AiService {
    @POST("/clothes/ai")
    suspend fun classifyCloth(
        @Header("Authorization") token: String,
        @Body imageUrlBody: Map<String, String>
    ): Response<AiResponse>
}
