package com.example.cloop

import android.content.Context

object TokenManager {
    private const val PREF_NAME = "auth_prefs"
    private const val KEY_ACCESS = "access_token"
    private const val KEY_REFRESH = "refresh_token"

    fun saveTokens(context: Context, accessToken: String, refreshToken: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString(KEY_ACCESS, accessToken)
            putString(KEY_REFRESH, refreshToken)
            apply()
        }
    }

    fun getRefreshToken(context: Context): String? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_REFRESH, null)
    }

    fun getAccessToken(context: Context): String? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_ACCESS, null)
    }

    fun clear(context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().clear().apply()
    }
}
