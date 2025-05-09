package com.example.cloop.data.model.auth

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)
