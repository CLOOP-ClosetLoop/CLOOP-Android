package com.example.cloop.data.model.auth

data class LoginResponse(
    val status: String,
    val access_token: String? = null,
    val nickname: String? = null,
    val refresh_token: String? = null,
    val userId: Int? = null,
    val googleId: String? = null
)
