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
                        Log.d("AutoLogin", "✅ 자동 로그인 성공: $accessToken")
                    } else {
                        Log.e("AutoLogin", "🚨 응답은 성공했지만 accessToken이 없음")
                        _autoLoginSuccess.postValue(false)
                    }
                } else {
                    Log.e("AutoLogin", "🚨 응답 실패: ${response.code()}")
                    _autoLoginSuccess.postValue(false)
                }
            }

            override fun onFailure(call: Call<AccessTokenResponse>, t: Throwable) {
                Log.e("AutoLogin", "🚨 네트워크 오류: ${t.message}")
                _autoLoginSuccess.postValue(false)
            }
        })
    }
}
