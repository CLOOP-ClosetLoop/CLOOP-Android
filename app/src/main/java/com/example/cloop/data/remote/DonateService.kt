package com.example.cloop.data.remote

import com.example.cloop.data.model.donate.DonationCloth
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface DonateService {
    @GET("/clothes/donation-candidates")
    suspend fun getDonationCandidates(
        @Header("Authorization") token: String
    ): Response<List<DonationCloth>>
}