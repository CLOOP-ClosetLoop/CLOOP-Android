package com.example.cloop.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.WindowInsetsAnimation
import com.example.cloop.data.model.auth.GoogleLoginRequest
import com.example.cloop.data.model.auth.LoginResponse
import com.example.cloop.data.remote.RetrofitClient

class AuthRepository {

    fun loginWithGoogle(idToken: String, onSuccess: (LoginResponse) -> Unit, onFailure: (Throwable) -> Unit) {
        val request = GoogleLoginRequest(idToken)

        RetrofitClient.authService.loginWithGoogle(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
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


    fun signupWithGoogle(idToken: String, onSuccess: (LoginResponse) -> Unit, onFailure: (Throwable) -> Unit) {
        val request = GoogleLoginRequest(idToken)

        RetrofitClient.authService.signupWithGoogle(request).enqueue(object : Callback<LoginResponse> {
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

