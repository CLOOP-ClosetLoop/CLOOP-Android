package com.example.cloop.data.remote

import retrofit2.Call
import com.example.cloop.data.model.auth.GoogleLoginRequest
import com.example.cloop.data.model.auth.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/google")
    fun loginWithGoogle(@Body body: GoogleLoginRequest): Call<LoginResponse>

    @POST("/auth/google/signup")
    fun signupWithGoogle(@Body body: GoogleLoginRequest): Call<LoginResponse>
}

