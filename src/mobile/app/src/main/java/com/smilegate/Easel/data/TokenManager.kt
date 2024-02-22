package com.smilegate.Easel.data

import android.content.Context
import android.content.SharedPreferences

object TokenManager {
    private const val PREF_NAME = "TokenPrefs"
    private const val KEY_ACCESS_TOKEN = "accessToken"
    private const val KEY_REFRESH_TOKEN = "refreshToken"

    private var sharedPreferences: SharedPreferences? = null

    // SharedPreferences 인스턴스 가져오기
    private fun getSharedPreferences(context: Context): SharedPreferences {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
        return sharedPreferences!!
    }

    // AccessToken 저장
    fun saveAccessToken(context: Context, accessToken: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_ACCESS_TOKEN, accessToken)
        editor.apply()
    }

    // RefreshToken 저장
    fun saveRefreshToken(context: Context, refreshToken: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_REFRESH_TOKEN, refreshToken)
        editor.apply()
    }

    // AccessToken 가져오기
    fun getAccessToken(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_ACCESS_TOKEN, null)
    }

    // RefreshToken 가져오기
    fun getRefreshToken(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_REFRESH_TOKEN, null)
    }

    // 토큰 삭제
    fun clearTokens(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.remove(KEY_ACCESS_TOKEN)
        editor.remove(KEY_REFRESH_TOKEN)
        editor.apply()
    }
}
