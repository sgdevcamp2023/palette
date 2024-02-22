package com.smilegate.Easel.domain.model

data class TimelineItem(
    val viewType: Int,
    val profileImg: Int?,
    val nickName: String,
    val userName: String,
    val timeAgo: String,
    val content: String?,
    val contentImg: Int?,
    val hashtag: String?,
    val quoteProfileImg: Int?,
    val quoteNickName: String?,
    val quoteUserName: String?,
    val quoteTimeAgo: String?,
    val quoteContent: String?,
    val quoteContentImg: Int?,
    val replys: Int?,
    val reposts: Int?,
    val like: Int?,
    val views: Int?,
)
