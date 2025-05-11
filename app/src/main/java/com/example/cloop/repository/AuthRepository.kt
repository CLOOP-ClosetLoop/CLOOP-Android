package com.example.cloop.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.cloop.data.model.auth.LoginResponse
import com.example.cloop.data.remote.RetrofitClient

class AuthRepository {

    fun loginWithGoogle(
        idToken: String,
        onSuccess: (LoginResponse) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val request = mapOf("id_token" to idToken)
        RetrofitClient.authService.loginWithGoogle(request)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        onSuccess(response.body()!!)
                    } else {
                        onFailure(Throwable("로그인 실패: ${response.code()}"))
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    onFailure(t)
                }
            })
    }

    fun signupWithGoogle(
        googleId: String,
        nickname: String,
        gender: String,
        onSuccess: (LoginResponse) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val request = mapOf(
            "googleId" to googleId,
            "nickname" to nickname,
            "gender" to gender
        )

        RetrofitClient.authService.signupWithGoogle(request)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        onSuccess(response.body()!!)
                    } else {
                        onFailure(Throwable("회원가입 실패: ${response.code()}"))
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    onFailure(t)
                }
            })
    }
}

