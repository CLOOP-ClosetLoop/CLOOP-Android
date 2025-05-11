package com.example.cloop.data.model.auth

data class GoogleLoginRequest(
    val googleId: String,
    val nickname: String,
    val gender: String
)
