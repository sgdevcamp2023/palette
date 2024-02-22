package com.smilegate.Easel.data

import com.smilegate.Easel.domain.model.HighlightItem
import com.smilegate.Easel.domain.model.TimelineItem

class ProfileTapRvDataHelper {
    companion object {
        fun getDataForTab(tabPosition: Int): List<TimelineItem> {
            return when (tabPosition) {
                0 -> generateDummyMyPostData() // 게시물
                1 -> generateDummyReplyData() // 답글
                2 -> generateDummyMyPostData() // 하이라이트
                3 -> generateDummyMediaData() // 미디어
                4 -> generateDummyLikedData() // 마음에 들어요
                else -> emptyList() // 기본적으로 빈 리스트 반환
            }
        }
    }
}

private fun generateDummyMyPostData(): List<TimelineItem> {
    val profileImgId = com.smilegate.Easel.R.drawable.sample_profile_img5
    val profileImgId1 = com.smilegate.Easel.R.drawable.sample_profile_img1
    val profileImgId2 = com.smilegate.Easel.R.drawable.sample_profile_img2
    val profileImgId3 = com.smilegate.Easel.R.drawable.sample_profile_img3
    val profileImgId4 = com.smilegate.Easel.R.drawable.sample_profile_img4

    val contentImgId = com.smilegate.Easel.R.drawable.sample_content_img1
    val contentImgId1 = com.smilegate.Easel.R.drawable.sample_content_img2
    val contentImgId2 = com.smilegate.Easel.R.drawable.sample_content_img3
    val contentImgId3 = com.smilegate.Easel.R.drawable.sample_content_img4

    val timelineList = mutableListOf<TimelineItem>()

    for (i in 0 until 8) {
        val viewType = if (i % 2 == 0) 0 else 1 // 랜덤한 뷰 타입 결정

        val timelineItem = when (viewType) {
            0 -> TimelineItem(
                viewType,
                profileImgId,
                "이원영",
                "@courtney81819",
                "1시간",
                "아 슈뢰딩거가 아닌가?ㅋ",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                2,
                1,
                null,
                24
            )
            1 -> TimelineItem(
                viewType,
                profileImgId2,
                "이상민",
                "@isangmi92157279",
                "32분",
                "비가 내리는 날이에요.\n추적이는 바닥을 보며 걷다보니 카페가 나와 커피를 사 봤어요.",
                contentImgId,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                4,
                2,
                5,
            )
            else -> TimelineItem(
                viewType,
                profileImgId2,
                "이상민",
                "@isangmi92157279",
                "32분",
                "비가 내리는 날이에요.\n추적이는 바닥을 보며 걷다보니 카페가 나와 커피를 사 봤어요.",
                contentImgId,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                4,
                2,
                5,
            )
        }

        timelineList.add(timelineItem)
    }

    return timelineList.shuffled()
}

private fun generateDummyReplyData(): List<TimelineItem> {
    val profileImgId = com.smilegate.Easel.R.drawable.sample_profile_img5
    val profileImgId1 = com.smilegate.Easel.R.drawable.sample_profile_img1
    val profileImgId2 = com.smilegate.Easel.R.drawable.sample_profile_img2
    val profileImgId3 = com.smilegate.Easel.R.drawable.sample_profile_img3
    val profileImgId4 = com.smilegate.Easel.R.drawable.sample_profile_img4

    val contentImgId = com.smilegate.Easel.R.drawable.sample_content_img1
    val contentImgId1 = com.smilegate.Easel.R.drawable.sample_content_img2
    val contentImgId2 = com.smilegate.Easel.R.drawable.sample_content_img3
    val contentImgId3 = com.smilegate.Easel.R.drawable.sample_content_img4

    val timelineList = mutableListOf<TimelineItem>()

    for (i in 0 until 8) {
        val viewType = if (i % 2 == 0) 0 else 1 // 랜덤한 뷰 타입 결정

        val timelineItem = when (viewType) {
            0 -> TimelineItem(
                viewType,
                profileImgId,
                "이원영",
                "@courtney81819",
                "1시간",
                "아 슈뢰딩거가 아닌가?ㅋ",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                2,
                1,
                null,
                24
            )
            1 -> TimelineItem(
                viewType,
                profileImgId2,
                "이상민",
                "@isangmi92157279",
                "32분",
                "비가 내리는 날이에요.\n추적이는 바닥을 보며 걷다보니 카페가 나와 커피를 사 봤어요.",
                contentImgId,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                4,
                2,
                5,
            )
            else -> TimelineItem(
                viewType,
                profileImgId2,
                "이상민",
                "@isangmi92157279",
                "32분",
                "비가 내리는 날이에요.\n추적이는 바닥을 보며 걷다보니 카페가 나와 커피를 사 봤어요.",
                contentImgId,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                4,
                2,
                5,
            )
        }

        timelineList.add(timelineItem)
    }

    return timelineList.shuffled()
}

fun generateDummyHighlightData(): List<HighlightItem> {

    val highlightItem = listOf(
        HighlightItem("프로필에 하이라이트 추가", "프로필에 게시물을 하이라이트하려면 Premium을 구독해\n야 합니다.", "Premium 구독하기"),
    )

    return highlightItem.shuffled()
}

private fun generateDummyMediaData(): List<TimelineItem> {
    val profileImgId = com.smilegate.Easel.R.drawable.sample_profile_img5
    val profileImgId1 = com.smilegate.Easel.R.drawable.sample_profile_img1
    val profileImgId2 = com.smilegate.Easel.R.drawable.sample_profile_img2
    val profileImgId3 = com.smilegate.Easel.R.drawable.sample_profile_img3
    val profileImgId4 = com.smilegate.Easel.R.drawable.sample_profile_img4

    val contentImgId = com.smilegate.Easel.R.drawable.sample_content_img1
    val contentImgId1 = com.smilegate.Easel.R.drawable.sample_content_img2
    val contentImgId2 = com.smilegate.Easel.R.drawable.sample_content_img3
    val contentImgId3 = com.smilegate.Easel.R.drawable.sample_content_img4

    val timelineList = mutableListOf<TimelineItem>()

    for (i in 0 until 8) {
        val viewType = if (i % 2 == 0) 0 else 1 // 랜덤한 뷰 타입 결정

        val timelineItem = when (viewType) {
            0 -> TimelineItem(
                viewType,
                profileImgId,
                "이원영",
                "@courtney81819",
                "1시간",
                "아 슈뢰딩거가 아닌가?ㅋ",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                2,
                1,
                null,
                24
            )
            1 -> TimelineItem(
                viewType,
                profileImgId2,
                "이상민",
                "@isangmi92157279",
                "32분",
                "비가 내리는 날이에요.\n추적이는 바닥을 보며 걷다보니 카페가 나와 커피를 사 봤어요.",
                contentImgId,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                4,
                2,
                5,
            )
            else -> TimelineItem(
                viewType,
                profileImgId2,
                "이상민",
                "@isangmi92157279",
                "32분",
                "비가 내리는 날이에요.\n추적이는 바닥을 보며 걷다보니 카페가 나와 커피를 사 봤어요.",
                contentImgId,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                4,
                2,
                5,
            )
        }

        timelineList.add(timelineItem)
    }

    return timelineList.shuffled()
}

private fun generateDummyLikedData(): List<TimelineItem> {
    val profileImgId = com.smilegate.Easel.R.drawable.sample_profile_img5
    val profileImgId1 = com.smilegate.Easel.R.drawable.sample_profile_img1
    val profileImgId2 = com.smilegate.Easel.R.drawable.sample_profile_img2
    val profileImgId3 = com.smilegate.Easel.R.drawable.sample_profile_img3
    val profileImgId4 = com.smilegate.Easel.R.drawable.sample_profile_img4

    val contentImgId = com.smilegate.Easel.R.drawable.sample_content_img1
    val contentImgId1 = com.smilegate.Easel.R.drawable.sample_content_img2
    val contentImgId2 = com.smilegate.Easel.R.drawable.sample_content_img3
    val contentImgId3 = com.smilegate.Easel.R.drawable.sample_content_img4

    val timelineList = mutableListOf<TimelineItem>()

    for (i in 0 until 8) {
        val viewType = if (i % 2 == 0) 0 else 1 // 랜덤한 뷰 타입 결정

        val timelineItem = when (viewType) {
            0 -> TimelineItem(
                viewType,
                profileImgId,
                "이원영",
                "@courtney81819",
                "1시간",
                "아 슈뢰딩거가 아닌가?ㅋ",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                2,
                1,
                null,
                24
            )
            1 -> TimelineItem(
                viewType,
                profileImgId2,
                "이상민",
                "@isangmi92157279",
                "32분",
                "비가 내리는 날이에요.\n추적이는 바닥을 보며 걷다보니 카페가 나와 커피를 사 봤어요.",
                contentImgId,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                4,
                2,
                5,
            )
            else -> TimelineItem(
                viewType,
                profileImgId2,
                "이상민",
                "@isangmi92157279",
                "32분",
                "비가 내리는 날이에요.\n추적이는 바닥을 보며 걷다보니 카페가 나와 커피를 사 봤어요.",
                contentImgId,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                4,
                2,
                5,
            )
        }

        timelineList.add(timelineItem)
    }

    return timelineList.shuffled()
}
fun refreshTimelineData() {
    //val shuffledTimelineList = ProfileTapRvDataHelper.getDataForTab().shuffled()
//    (binding.rvProfile.adapter as? TimelineRecyclerViewAdapter)?.updateData(shuffledTimelineList)
//
//    binding.swipeRefreshLayout.isRefreshing = false
}
