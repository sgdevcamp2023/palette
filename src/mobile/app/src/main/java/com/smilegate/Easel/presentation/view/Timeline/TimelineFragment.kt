package com.smilegate.Easel.presentation.view.Timeline

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentStartBinding
import com.smilegate.Easel.databinding.FragmentTimelineBinding
import com.smilegate.Easel.domain.model.TimelineItem
import com.smilegate.Easel.presentation.adapter.TimelineAdapter

class TimelineFragment : Fragment() {
    private lateinit var binding: FragmentTimelineBinding

    private lateinit var navController: NavController

    private var doubleBackPressed = false

    private var isFabOpen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimelineBinding.inflate(inflater, container, false)

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

        // 바텀 네비게이션바 보이기
        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigation?.visibility = View.VISIBLE

        val viewPager: ViewPager = binding.root.findViewById(R.id.viewPager)
        setupViewPager(viewPager)

        val tabLayout: TabLayout = binding.root.findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), R.color.Blue_500))

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

        setFABClickEvent()

    }

    private fun isCurrentFragment(): Boolean {
        return true
    }

    private fun setupViewPager(viewPager: ViewPager) {
        // 여러 개의 프래그먼트를 추가하여 ViewPager에 연결
        val adapter = TimelineAdapter(childFragmentManager)
        adapter.addFragment(ForYouFragment(), "추천")
        adapter.addFragment(FollowingFragment(), "팔로우 중")
        viewPager.adapter = adapter
    }

    private fun setFABClickEvent() {
        // 플로팅 버튼 클릭시 애니메이션 동작 기능
        binding.fabMain.setOnClickListener {
            toggleFab()
        }

        // 플로팅 버튼 클릭 이벤트 - 캡처
        binding.fabImage.setOnClickListener {

        }

        // 플로팅 버튼 클릭 이벤트 - 공유
        binding.fabGif.setOnClickListener {

        }

        // 플로팅 버튼 클릭 이벤트 - 공유
        binding.fabWrite.setOnClickListener {

        }
    }

    private fun toggleFab() {
        // 플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션
        if (isFabOpen) {
            ObjectAnimator.ofFloat(binding.fabWrite, "translationX", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabWrite, "translationY", 0f).apply { start() }

            ObjectAnimator.ofFloat(binding.fabGif, "translationX", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabGif, "translationY", 0f).apply { start() }

            ObjectAnimator.ofFloat(binding.fabImage, "translationX", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabImage, "translationY", 0f).apply { start() }

            ObjectAnimator.ofFloat(binding.fabMain, View.ROTATION, 180f, 0f).apply { start() }
        } else { // 플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션
            ObjectAnimator.ofFloat(binding.fabWrite, "translationX", 16 * 1.25f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabWrite, "translationY", -298 * 1.25f).apply { start() }

            ObjectAnimator.ofFloat(binding.fabGif, "translationX", -170 * 1.25f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabGif, "translationY", -170 * 1.25f).apply { start() }

            ObjectAnimator.ofFloat(binding.fabImage, "translationX", -260 * 1.25f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabImage, "translationY", 30 * 1.25f).apply { start() }

            ObjectAnimator.ofFloat(binding.fabMain, View.ROTATION, 180f, 0f).apply { start() }
        }

        isFabOpen = !isFabOpen

    }
}
