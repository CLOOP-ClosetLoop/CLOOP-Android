package com.example.cloop.data.remote

import com.example.cloop.data.model.cloth.AiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AiService {
    @POST("/clothes/ai")
    suspend fun classifyCloth(
        @Body imageUrlBody: Map<String, String>
    ): Response<AiResponse>
}
