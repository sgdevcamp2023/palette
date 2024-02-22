package com.smilegate.Easel.domain.repository

import com.smilegate.Easel.domain.api.ApiService
import com.smilegate.Easel.domain.model.auth.EmailRequest

class UserRepository(private val apiService: ApiService) {
    suspend fun verifyEmail(email: String): Boolean {
        val request = EmailRequest(email)
        val response = apiService.verifyEmail(request)
        return response.isSuccessful && response.body()?.isDuplicated == false
    }
}
