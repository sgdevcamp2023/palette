package com.smilegate.Easel.presentation.view.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smilegate.Easel.MainActivity
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentLikedBinding
import com.smilegate.Easel.domain.model.TimelineItem
import com.smilegate.Easel.presentation.adapter.TimelineRecyclerViewAdapter

class LikedFragment : Fragment() {
    private lateinit var binding: FragmentLikedBinding

    private lateinit var navController: NavController

    private var savedScrollPosition: Int = 0

    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLikedBinding.inflate(inflater, container, false)

        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_container)
        toolbar.visibility = View.GONE

        navController = findNavController()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // MainActivity의 참조를 가져옴
        if (context is MainActivity) {
            mainActivity = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.requestFocus()

        val likedList = generateDummyTimelineData()

        val adapter = TimelineRecyclerViewAdapter(requireContext(), likedList)
        binding.rvLiked.adapter = adapter
        binding.rvLiked.layoutManager = LinearLayoutManager(requireContext())

        // 스크롤 리스너를 이용하여 스크롤 위치 저장
        binding.rvLiked.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            savedScrollPosition = scrollY
        }

        // 저장된 스크롤 위치 복원
        binding.rvLiked.post {
            binding.rvLiked.scrollTo(0, savedScrollPosition)
        }
    }

    private fun generateDummyTimelineData(): List<TimelineItem> {
        val profileImgId = R.drawable.sample_profile_img5
        val profileImgId1 = R.drawable.sample_profile_img1
        val profileImgId2 = R.drawable.sample_profile_img2
        val contentImgId = R.drawable.sample_content_img1
        val contentImgId1 = R.drawable.sample_content_img2
        val contentImgId2 = R.drawable.sample_content_img3

        val timelineList = mutableListOf<TimelineItem>()

        for (i in 0 until 8) {
            val viewType = i % 3 // 랜덤한 뷰 타입 결정

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
                    profileImgId,
                    "이원영",
                    "@courtney81819",
                    "21시간 전",
                    "엥 실화?",
                    contentImgId1,
                    2,
                    1,
                    null,
                    24,
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
                    profileImgId,
                    "이원영",
                    "@courtney81819",
                    "21시간 전",
                    "엥 실화?",
                    contentImgId1,
                    null,
                    4,
                    2,
                    5,
                )
                else -> TimelineItem(
                    viewType,
                    profileImgId1,
                    "박희원",
                    "@_Parking1_",
                    "18분",
                    "타래 스타트",
                    contentImgId1,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    1,
                    1,
                    null,
                    114,
                )
            }

            timelineList.add(timelineItem)
        }

        return timelineList.shuffled()
    }

    override fun onPause() {
        super.onPause()
        savedScrollPosition = binding.rvLiked.scrollY
    }
}
