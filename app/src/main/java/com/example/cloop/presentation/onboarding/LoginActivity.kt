package com.example.cloop.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cloop.MainActivity
import com.example.cloop.TokenManager
import com.example.cloop.databinding.ActivityLoginBinding
import com.example.cloop.data.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var googleSignInClient: GoogleSignInClient
    private val GOOGLE_SIGN_IN = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel.autoLoginSuccess.observe(this) { success ->
            if (success) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        loginViewModel.attemptAutoLogin(this)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("282924798851-2lhulrjvhnhs963tnjq88n4iq72a8lia.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.loginBtn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account.idToken

                Log.d("Login", "email: ${account.email}")
                Log.d("Login", "displayName: ${account.displayName}")
                Log.d("Login", "idToken: $idToken")

                if (idToken != null) {
                    AuthRepository().loginWithGoogle(
                        idToken,
                        onSuccess = { response ->
                            if (response.status == "login") {
                                Log.d("Login", "로그인 성공: ${response.access_token}")

                                TokenManager.saveTokens(
                                    context = this,
                                    accessToken = response.access_token ?: "",
                                    refreshToken = response.refresh_token ?: ""
                                )

                                Log.d("TokenCheck", "받은 accessToken: ${response.access_token}")
                                Log.d("TokenCheck", "받은 refreshToken: ${response.refresh_token}")

                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            } else if (response.status.contains("회원가입이 필요")) {
                                // 비회원이면 회원가입 요청
                                val googleId = response.googleId!!
                                AuthRepository().signupWithGoogle(
                                    googleId,
                                    "닉네임 입력",
                                    "여성",
                                    onSuccess = { signUpRes ->
                                        Log.d("Login", "회원가입 성공: ${signUpRes.access_token}")

                                        TokenManager.saveTokens(
                                            context = this,
                                            accessToken = signUpRes.access_token ?: "",
                                            refreshToken = signUpRes.refresh_token ?: ""
                                        )

                                        startActivity(Intent(this, MainActivity::class.java))
                                        finish()
                                    },
                                    onFailure = { e ->
                                    }
                                )
                            }
                        },
                        onFailure = { e ->
                        }
                    )
                } else {
                    Log.e("Login", "idToken == null")
                }
            } catch (e: ApiException) {
                Log.e("Login", "Google 로그인 실패: ${e.statusCode}")
            }
        }
    }

}