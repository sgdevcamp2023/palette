package com.smilegate.Easel.domain.model.user

data class UserProfileResponse(
    val backgroundImagePath: String,
    val profileImagePath: String,
    val nickname: String,
    val username: String,
    val introduce: String,
    val websitePath: String,
    val joinedAt: String,
    val followingCount: Long,
    val followerCount: Long
)
