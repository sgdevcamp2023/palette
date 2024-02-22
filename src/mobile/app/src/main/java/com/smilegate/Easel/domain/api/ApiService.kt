package com.smilegate.Easel.domain.api

import com.smilegate.Easel.domain.model.EmailAuth
import com.smilegate.Easel.domain.model.EmailRequest
import com.smilegate.Easel.domain.model.EmailResponse
import com.smilegate.Easel.domain.model.TemporaryJoinRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth")
    suspend fun sendCode(@Body request: EmailAuth): Response<ResponseBody>

    @POST("api/users/verify-email")
    suspend fun verifyEmail(@Body request: EmailRequest): Response<EmailResponse>

    @POST("api/users/temporary-join")
    suspend fun temporaryJoin(@Body request: TemporaryJoinRequest): Response<Unit>
}
