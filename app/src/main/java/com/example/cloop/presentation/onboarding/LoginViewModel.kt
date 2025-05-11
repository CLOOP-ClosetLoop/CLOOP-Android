package com.example.cloop.presentation.onboarding

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cloop.TokenManager
import com.example.cloop.data.model.auth.AccessTokenResponse
import com.example.cloop.data.model.auth.RefreshTokenRequest
import com.example.cloop.data.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val _autoLoginSuccess = MutableLiveData<Boolean>()
    val autoLoginSuccess: LiveData<Boolean> get() = _autoLoginSuccess

    fun attemptAutoLogin(context: Context) {
        val refreshToken = TokenManager.getRefreshToken(context)
        if (refreshToken.isNullOrEmpty()) {
            _autoLoginSuccess.postValue(false)
            return
        }

        val request = RefreshTokenRequest(refreshToken)

        RetrofitClient.authService.refreshAccessToken(request).enqueue(object :
            Callback<AccessTokenResponse> {
            override fun onResponse(call: Call<AccessTokenResponse>, response: Response<AccessTokenResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val accessToken = response.body()?.accessToken ?: ""
                    if (accessToken.isNotEmpty()) {
                        TokenManager.saveTokens(context, accessToken, refreshToken)
                        _autoLoginSuccess.postValue(true)
                    } else {
                        _autoLoginSuccess.postValue(false)
                    }
                } else {
                    _autoLoginSuccess.postValue(false)
                }
            }

            override fun onFailure(call: Call<AccessTokenResponse>, t: Throwable) {
                _autoLoginSuccess.postValue(false)
            }
        })
    }
}
