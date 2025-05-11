package com.example.cloop.data.remote

import com.example.cloop.data.model.auth.AccessTokenResponse
import retrofit2.Call
import com.example.cloop.data.model.auth.GoogleLoginRequest
import com.example.cloop.data.model.auth.LoginResponse
import com.example.cloop.data.model.auth.RefreshTokenRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/google")
    fun loginWithGoogle(@Body body: Map<String, String>): Call<LoginResponse>

    @POST("/auth/google/signup")
    fun signupWithGoogle(@Body body: Map<String, String>): Call<LoginResponse>

    @POST("/auth/refresh")
    fun refreshAccessToken(@Body request: RefreshTokenRequest): Call<AccessTokenResponse>

}