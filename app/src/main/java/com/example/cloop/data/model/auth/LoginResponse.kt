package com.example.cloop.data.model.auth

data class LoginResponse(
    val status: String,
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val nickname: String? = null,
    val userId: Int? = null,
    val googleId: String? = null  // "회원가입 필요" 응답에 포함
)
