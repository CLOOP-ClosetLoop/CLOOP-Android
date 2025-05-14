package com.example.cloop.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "http://54.180.9.38:8080/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val authService: AuthService = retrofit.create(AuthService::class.java)

    val clothService: ClothService = retrofit.create(ClothService::class.java)

    val aiService: AiService by lazy { retrofit.create(AiService::class.java) }

    val donateService: DonateService = retrofit.create(DonateService::class.java)

    val lookService: LookService = retrofit.create(LookService::class.java)



    fun provideAuthServiceWithToken(token: String): AuthService {
        val clientWithAuth = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofitWithAuth = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clientWithAuth)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofitWithAuth.create(AuthService::class.java)
    }

}