package com.smilegate.Easel.domain.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.smilegate.Easel.domain.api.ApiService
import com.smilegate.Easel.domain.model.auth.EmailAuth
import java.io.IOException

class SendCodeRepository(private val apiService: ApiService) {
    private val _codeLiveData = MutableLiveData<String?>()
    private val codeLiveData: MutableLiveData<String?> = _codeLiveData

    suspend fun sendCode(email: String, payload: String) {
        val request = EmailAuth(email, payload)
        try {
            val response = apiService.sendCode(request)
            if (response.isSuccessful) {
                // 성공적인 응답 처리
                val responseBody = response.body()?.string()
                if (responseBody != null) {
                    codeLiveData.postValue(responseBody)
                } else {
                    // 서버가 빈 응답을 반환한 경우 처리
                }
            } else {
                // 실패한 응답 처리
                val errorMessage = response.errorBody()?.string()
                if (!errorMessage.isNullOrEmpty()) {
                    Log.e("SendCodeRepository", "Error: $errorMessage")
                } else {
                    Log.e("SendCodeRepository", "Failed to send code. Response code: ${response.code()}")
                }
            }
        } catch (e: IOException) {
            Log.e("SendCodeRepository", "Network error: ${e.message}")
        } catch (e: Exception) {
            Log.e("SendCodeRepository", "Unexpected error: ${e.message}")
        }
    }
}
