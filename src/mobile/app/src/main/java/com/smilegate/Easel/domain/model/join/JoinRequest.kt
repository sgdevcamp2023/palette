package com.smilegate.Easel.domain.model.join

data class JoinRequest(
    val email: String,
    val password: String,
    val username: String,
    val nickname: String,
    val introduce: String? = null,
    val profileImagePath: String? = null,
    val websitePath: String? = null
)
