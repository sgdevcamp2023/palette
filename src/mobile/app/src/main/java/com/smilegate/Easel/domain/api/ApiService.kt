package com.smilegate.Easel.domain.api

import com.smilegate.Easel.domain.model.auth.EmailAuth
import com.smilegate.Easel.domain.model.auth.EmailRequest
import com.smilegate.Easel.domain.model.auth.EmailResponse
import com.smilegate.Easel.domain.model.join.JoinRequest
import com.smilegate.Easel.domain.model.join.TemporaryJoinRequest
import com.smilegate.Easel.domain.model.join.VerifyUsernameRequest
import com.smilegate.Easel.domain.model.join.VerifyUsernameResponse
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

    @POST("api/users/verify-username")
    suspend fun verifyUsername(@Body request: VerifyUsernameRequest): Response<VerifyUsernameResponse>

    @POST("api/users/join")
    suspend fun joinUser(@Body joinRequest: JoinRequest): Response<Unit>
}
