package com.smilegate.Easel.domain.repository

import android.util.Log
import com.smilegate.Easel.domain.api.ApiService
import com.smilegate.Easel.domain.model.join.JoinRequest

class JoinRepository(private val apiService: ApiService) {

    suspend fun joinUser(joinRequest: JoinRequest) {
        try {
            val response = apiService.joinUser(joinRequest)

            if (response.isSuccessful) {
                // 성공적으로 응답을 받았을 때의 처리
                // 여기에서는 특별히 할 일이 없다면 그냥 지나가도 됩니다.
            } else {
                // 서버로부터 오류 응답을 받았을 때의 처리
                // 예를 들어, 오류 메시지를 출력하거나 특정 동작을 수행할 수 있습니다.
                val errorBody = response.errorBody()?.string()
                Log.e("UserRepository", "Error: $errorBody")
                Log.e("UserRepository","Failed to join user. Response code: ${response.code()}")
            }
        } catch (e: Exception) {
            // 네트워크 오류 등 예외 발생 시의 처리
            Log.e("UserRepository", "Error: ${e.message}", e)
            Log.e("UserRepository","Failed to join user. Error: ${e.message}")
        }
    }
}
