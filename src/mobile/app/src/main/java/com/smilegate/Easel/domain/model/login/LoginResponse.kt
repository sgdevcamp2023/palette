package com.smilegate.Easel.domain.model.login

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)
