package com.smilegate.Easel.domain.model

data class TimelineItem(
    val profileImg: Int,
    val nickName: String,
    val userName: String,
    val timeAgo: String,
    val content: String,
    val contentImg: Int,
    val hashtag: String,
    val replys: Int,
    val reposts: Int,
    val like: Int,
    val views: Int
)
