package com.example.cloop.data.remote

import com.example.cloop.data.model.donate.DonationCloth
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface DonateService {
    @GET("/clothes/donation-candidates")
    suspend fun getDonationCandidates(
        @Header("Authorization") token: String
    ): Response<List<DonationCloth>>


    @PATCH("/clothes/{clothId}/donate")
    suspend fun confirmDonation(
        @Header("Authorization") token: String,
        @Path("clothId") clothId: Int,
        @Body body: Map<String, Boolean> = mapOf("confirmed" to true)
    ): Response<Unit>  // 또는 Response<DonationResult> 원하면 추후 확장

}