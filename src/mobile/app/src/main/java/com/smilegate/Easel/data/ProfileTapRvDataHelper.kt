package com.smilegate.Easel.data

import com.smilegate.Easel.R
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

fun generateDummyMyPostData(): List<TimelineItem> {
    val profileImgId = R.drawable.sample_profile_img5
    val profileImgId1 = R.drawable.sample_profile_img1
    val profileImgId2 = R.drawable.sample_profile_img2
    val profileImgId3 = R.drawable.sample_profile_img3
    val profileImgId4 = R.drawable.sample_profile_img4

    val contentImgId = R.drawable.sample_content_img1
    val contentImgId1 = R.drawable.sample_content_img2
    val contentImgId2 = R.drawable.sample_content_img3
    val contentImgId3 = R.drawable.sample_content_img4

    val timelineList = listOf(
        TimelineItem(profileImgId, "이원영", "@courtney81819", "1시간",
            "아 슈뢰딩거가 아닌가?ㅋ", null, null,
            2, 1, null, 24),


        TimelineItem(profileImgId2, "이상민", "@isangmi92157279", "32분",
            "비가 내리는 날이에요.\n추적이는 바닥을 보며 걷다보니 카페가 나와 커피를 사 봤어요.", contentImgId, null,
            4, 2, 5, 121),

        TimelineItem(profileImgId1, "박희원", "@_Parking1_", "18분",
            "타래 스타트", contentImgId1, null,
            1, 1, null, 114),

        TimelineItem(profileImgId4, "김도율", "@doxxx93", "8분",
            "테스트", null, null,
            1, null, 2, 32),

        TimelineItem(profileImgId2, "이상민", "@isangmi92157279", "1주",
            "커피는 아이스 아메리카노에요.\n컵에 스며든 물방울처럼, 제 마음을 촉촉하게 만들어 주네요..^^", contentImgId2, null,
            3, 4, 2, 89),

        TimelineItem(profileImgId3, "김도현", "@KittenDiger", "4일",
            "테스트 인용의 인용", null, null,
            1, null, null, 30),

        TimelineItem(profileImgId, "이원영", "@courtney81819", "3시간",
            "청하 로제", contentImgId3, null,
            3, null, 2, 334),

        TimelineItem(profileImgId3, "김도현", "@KittenDiger", "4일",
            "구글인ㅇㅎㅇ의 인용의 인용", null, null,
            1, 2, null, 50),
    )

    return timelineList.shuffled()
}

fun generateDummyReplyData(): List<TimelineItem> {
    val profileImgId = R.drawable.sample_profile_img5
    val profileImgId1 = R.drawable.sample_profile_img1
    val profileImgId2 = R.drawable.sample_profile_img2
    val profileImgId4 = R.drawable.sample_profile_img4

    val contentImgId = R.drawable.sample_content_img1
    val contentImgId1 = R.drawable.sample_content_img2
    val contentImgId2 = R.drawable.sample_content_img3

    val timelineList = listOf(

        TimelineItem(profileImgId1, "박희원", "@_Parking1_", "18분",
            "타래 스타트", contentImgId1, null,
            1, 1, null, 114),

        TimelineItem(profileImgId, "이원영", "@courtney81819", "1시간",
            "아 슈뢰딩거가 아닌가?ㅋ", null, null,
            2, 1, null, 24),

        TimelineItem(profileImgId2, "이상민", "@isangmi92157279", "32분",
            "비가 내리는 날이에요.\n추적이는 바닥을 보며 걷다보니 카페가 나와 커피를 사 봤어요.", contentImgId, null,
            4, 2, 5, 121),

        TimelineItem(profileImgId4, "김도율", "@doxxx93", "8분",
            "테스트", null, null,
            1, null, 2, 32),

        TimelineItem(profileImgId2, "이상민", "@isangmi92157279", "1주",
            "커피는 아이스 아메리카노에요.\n컵에 스며든 물방울처럼, 제 마음을 촉촉하게 만들어 주네요..^^", contentImgId2, null,
            3, 4, 2, 89),
    )

    return timelineList.shuffled()
}

fun generateDummyHighlightData(): List<HighlightItem> {

    val highlightItem = listOf(
        HighlightItem("프로필에 하이라이트 추가", "프로필에 게시물을 하이라이트하려면 Premium을 구독해\n야 합니다.", "Premium 구독하기"),
    )

    return highlightItem.shuffled()
}

fun generateDummyMediaData(): List<TimelineItem> {
    val profileImgId = R.drawable.sample_profile_img5
    val profileImgId2 = R.drawable.sample_profile_img2
    val profileImgId3 = R.drawable.sample_profile_img3

    val contentImgId = R.drawable.sample_content_img1
    val contentImgId2 = R.drawable.sample_content_img3
    val contentImgId3 = R.drawable.sample_content_img4

    val timelineList = listOf(

        TimelineItem(profileImgId, "이원영", "@courtney81819", "3시간",
            "청하 로제", contentImgId3, null,
            3, null, 2, 334),

        TimelineItem(profileImgId3, "김도현", "@KittenDiger", "4일",
            "구글인ㅇㅎㅇ의 인용의 인용", null, null,
            1, 2, null, 50),

        TimelineItem(profileImgId, "이원영", "@courtney81819", "1시간",
            "아 슈뢰딩거가 아닌가?ㅋ", null, null,
            2, 1, null, 24),


        TimelineItem(profileImgId2, "이상민", "@isangmi92157279", "32분",
            "비가 내리는 날이에요.\n추적이는 바닥을 보며 걷다보니 카페가 나와 커피를 사 봤어요.", contentImgId, null,
            4, 2, 5, 121),

        TimelineItem(profileImgId2, "이상민", "@isangmi92157279", "1주",
            "커피는 아이스 아메리카노에요.\n컵에 스며든 물방울처럼, 제 마음을 촉촉하게 만들어 주네요..^^", contentImgId2, null,
            3, 4, 2, 89),

        TimelineItem(profileImgId3, "김도현", "@KittenDiger", "4일",
            "테스트 인용의 인용", null, null,
            1, null, null, 30),
    )

    return timelineList.shuffled()
}

fun generateDummyLikedData(): List<TimelineItem> {
    val profileImgId = R.drawable.sample_profile_img5
    val profileImgId1 = R.drawable.sample_profile_img1
    val profileImgId2 = R.drawable.sample_profile_img2
    val profileImgId3 = R.drawable.sample_profile_img3
    val profileImgId4 = R.drawable.sample_profile_img4

    val contentImgId1 = R.drawable.sample_content_img2
    val contentImgId2 = R.drawable.sample_content_img3
    val contentImgId3 = R.drawable.sample_content_img4

    val timelineList = listOf(
        TimelineItem(profileImgId, "이원영", "@courtney81819", "1시간",
            "아 슈뢰딩거가 아닌가?ㅋ", null, null,
            2, 1, null, 24),

        TimelineItem(profileImgId1, "박희원", "@_Parking1_", "18분",
            "타래 스타트", contentImgId1, null,
            1, 1, null, 114),

        TimelineItem(profileImgId4, "김도율", "@doxxx93", "8분",
            "테스트", null, null,
            1, null, 2, 32),

        TimelineItem(profileImgId2, "이상민", "@isangmi92157279", "1주",
            "커피는 아이스 아메리카노에요.\n컵에 스며든 물방울처럼, 제 마음을 촉촉하게 만들어 주네요..^^", contentImgId2, null,
            3, 4, 2, 89),

        TimelineItem(profileImgId, "이원영", "@courtney81819", "3시간",
            "청하 로제", contentImgId3, null,
            3, null, 2, 334),

        TimelineItem(profileImgId3, "김도현", "@KittenDiger", "4일",
            "구글인ㅇㅎㅇ의 인용의 인용", null, null,
            1, 2, null, 50),
    )

    return timelineList.shuffled()
}
fun refreshTimelineData() {
    //val shuffledTimelineList = ProfileTapRvDataHelper.getDataForTab().shuffled()
//    (binding.rvProfile.adapter as? TimelineRecyclerViewAdapter)?.updateData(shuffledTimelineList)
//
//    binding.swipeRefreshLayout.isRefreshing = false
}
