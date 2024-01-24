package com.smilegate.Easel.presentation.view.timeline

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentForYouBinding
import com.smilegate.Easel.domain.model.TimelineItem
import com.smilegate.Easel.presentation.adapter.TimelineRecyclerViewAdapter
import com.smilegate.Easel.presentation.view.myPage.MyPageFragment

class ForYouFragment : Fragment() {
    private lateinit var binding: FragmentForYouBinding

    private lateinit var navController: NavController

    private var doubleBackPressed = false

    private var savedScrollPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForYouBinding.inflate(inflater, container, false)

        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_container)

        val profileButton = toolbar.findViewById<ImageView>(R.id.back_btn)
        profileButton.setImageResource(R.drawable.ic_circle_person)
        profileButton.visibility = View.VISIBLE
        profileButton.setOnClickListener {
            findNavController().navigate(R.id.myPageFragment)
        }

        val settingButton = toolbar.findViewById<ImageView>(R.id.else_btn)
        settingButton.setImageResource(R.drawable.ic_setting)
        settingButton.visibility = View.VISIBLE

        navController = findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 바텀 네비게이션바 보이기
        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigation?.visibility = View.VISIBLE

        // 뒤로가기 버튼을 처리하는 부분
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (isCurrentFragment()) {
                    // 현재 프래그먼트에서 뒤로가기 버튼 처리
                    if (doubleBackPressed) {
                        // 두 번째 눌림
                        requireActivity().finish()
                    } else {
                        // 첫 번째 눌림
                        doubleBackPressed = true
                        // 2초 내에 두 번 누르지 않으면 초기화
                        Handler(Looper.getMainLooper()).postDelayed({
                            doubleBackPressed = false
                        }, 2000)
                    }
                    return@setOnKeyListener true
                }
            }
            false
        }

        val timelineList = generateDummyTimelineData()

        val adapter = TimelineRecyclerViewAdapter(requireContext(), timelineList)
        binding.rvTimeline.adapter = adapter
        binding.rvTimeline.layoutManager = LinearLayoutManager(requireContext())

        // 스크롤 리스너를 이용하여 스크롤 위치 저장
        binding.ForYouScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            savedScrollPosition = scrollY
        }

        // 저장된 스크롤 위치 복원
        binding.ForYouScrollView.post {
            binding.ForYouScrollView.scrollTo(0, savedScrollPosition)
        }
    }

    private fun isCurrentFragment(): Boolean {
        return true
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

        return listOf(
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
    }

    override fun onPause() {
        super.onPause()
        savedScrollPosition = binding.ForYouScrollView.scrollY
    }
}
