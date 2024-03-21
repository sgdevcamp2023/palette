package com.smilegate.Easel.presentation.view.myPage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smilegate.Easel.MainActivity
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentBookMarkBinding
import com.smilegate.Easel.domain.model.TimelineItem
import com.smilegate.Easel.presentation.adapter.TimelineRecyclerViewAdapter

class BookMarkFragment : Fragment() {
    private lateinit var binding: FragmentBookMarkBinding

    private lateinit var navController: NavController

    private var savedScrollPosition: Int = 0

    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookMarkBinding.inflate(inflater, container, false)

        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_container)
        toolbar.visibility = View.VISIBLE

        val backButton = toolbar.findViewById<ImageView>(R.id.back_btn)
        backButton.setImageResource(R.drawable.ic_left_stick_arrow)
        backButton.visibility = View.VISIBLE
        backButton.setOnClickListener {
            findNavController().navigate(R.id.timelineFragment)
        }

        val settingButton = toolbar.findViewById<ImageView>(R.id.else_btn)
        settingButton.setImageResource(R.drawable.ic_three_dot)
        settingButton.visibility = View.VISIBLE

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

        // 바텀 네비게이션바 보이기
        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigation?.visibility = View.VISIBLE

        // 뒤로가기 버튼을 처리하는 부분
        view.isFocusableInTouchMode = true
        view.requestFocus()

        val bookMarkList = generateDummyTimelineData()

        val adapter = TimelineRecyclerViewAdapter(requireContext(), bookMarkList)
        binding.rvBookMark.adapter = adapter
        binding.rvBookMark.layoutManager = LinearLayoutManager(requireContext())

        // 스크롤 리스너를 이용하여 스크롤 위치 저장
        binding.rvBookMark.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            savedScrollPosition = scrollY
        }

        // 저장된 스크롤 위치 복원
        binding.rvBookMark.post {
            binding.rvBookMark.scrollTo(0, savedScrollPosition)
        }
    }

    private fun generateDummyTimelineData(): List<TimelineItem> {
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

    override fun onPause() {
        super.onPause()
        savedScrollPosition = binding.rvBookMark.scrollY
    }
}