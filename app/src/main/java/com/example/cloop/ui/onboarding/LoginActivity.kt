package com.example.cloop.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cloop.MainActivity
import com.example.cloop.R
import com.example.cloop.data.model.auth.LoginResponse
import com.example.cloop.databinding.ActivityLoginBinding
import com.example.cloop.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var googleSignInClient: GoogleSignInClient
    private val GOOGLE_SIGN_IN = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. 구글 로그인 옵션 설정
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("282924798851-2lhulrjvhnhs963tnjq88n4iq72a8lia.apps.googleusercontent.com") // 콘솔에서 받은 웹클라이언트 ID
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // 2. 버튼 클릭 시 로그인 실행
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
                            // 회원이면 로그인 성공
                            if (response.status == "login") {
                                Log.d("Login", "✅ 로그인 성공: ${response.accessToken}")
                                // TODO: 토큰 저장 등
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            } else if (response.status.contains("회원가입이 필요")) {
                                // 비회원이면 회원가입 요청
                                val googleId = response.googleId!!
                                AuthRepository().signupWithGoogle(
                                    googleId,
                                    "닉네임 입력",  // TODO: 사용자 입력 받아도 됨
                                    "여성",
                                    onSuccess = { signUpRes ->
                                        Log.d("Login", "✅ 회원가입 성공: ${signUpRes.accessToken}")
                                        startActivity(Intent(this, MainActivity::class.java))
                                        finish()
                                    },
                                    onFailure = { e ->
                                        Log.e("Login", "❌ 회원가입 실패: ${e.message}")
                                    }
                                )
                            }
                        },
                        onFailure = { e ->
                            Log.e("Login", "❌ 로그인 실패: ${e.message}")
                        }
                    )
                } else {
                    Log.e("Login", "❌ idToken == null")
                }
            } catch (e: ApiException) {
                Log.e("Login", "Google 로그인 실패: ${e.statusCode}")
            }
        }
    }

}