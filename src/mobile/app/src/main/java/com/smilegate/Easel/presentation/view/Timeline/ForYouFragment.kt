package com.smilegate.Easel.presentation.view.Timeline

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
            findNavController().navigate(R.id.profileFragment)
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

        val adapter = TimelineRecyclerViewAdapter(timelineList)
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
        val profileImgId = R.drawable.bg_timeline_profile_img
        val contentImgId = R.drawable.bg_timeline_content_img

        return listOf(
            TimelineItem(profileImgId, "SBS 뉴스", "@SBS8news", "18분",
                "god “전성기때도 제작 된 적 없는 공연 실황 영화, 신기해”", contentImgId, "#SBS뉴스",
                4, 4, 2, 1032),

            TimelineItem(profileImgId, "SBS 뉴스", "@SBS8news", "18분",
                "god “전성기때도 제작 된 적 없는 공연 실황 영화, 신기해”", null, "#SBS뉴스",
                4, 4, 2, 1032),

            TimelineItem(profileImgId, "SBS 뉴스", "@SBS8news", "18분",
                "god “전성기때도 제작 된 적 없는 공연 실황 영화, 신기해”", null, "#SBS뉴스",
                4, 4, 2, 1032),

            TimelineItem(profileImgId, "SBS 뉴스", "@SBS8news", "18분",
                "god “전성기때도 제작 된 적 없는 공연 실황 영화, 신기해”", contentImgId, "#SBS뉴스",
                4, 4, 2, 1032),

            TimelineItem(profileImgId, "SBS 뉴스", "@SBS8news", "18분",
                "god “전성기때도 제작 된 적 없는 공연 실황 영화, 신기해”", null, "#SBS뉴스",
                4, 4, 2, 1032),

            TimelineItem(profileImgId, "SBS 뉴스", "@SBS8news", "18분",
                "god “전성기때도 제작 된 적 없는 공연 실황 영화, 신기해”", contentImgId, "#SBS뉴스",
                4, 4, 2, 1032),
        )
    }

    override fun onPause() {
        super.onPause()
        savedScrollPosition = binding.ForYouScrollView.scrollY
    }
}
