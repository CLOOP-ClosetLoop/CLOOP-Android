package com.example.cloop.data.repository

import com.example.cloop.data.model.donate.DonationCloth
import com.example.cloop.data.remote.DonateService

class DonateRepository(private val donateService: DonateService) {
    suspend fun getDonationCandidates(token: String): List<DonationCloth>? {
        return try {
            val response = donateService.getDonationCandidates("Bearer $token")
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }


    suspend fun confirmDonation(token: String, clothId: Int): Boolean {
        return try {
            val response = donateService.confirmDonation("Bearer $token", clothId)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}