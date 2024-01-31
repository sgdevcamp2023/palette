package com.smilegate.Easel.presentation.view.profile

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.GestureDetector
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.clans.fab.FloatingActionButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.smilegate.Easel.R
import com.smilegate.Easel.data.ProfileTapRvDataHelper
import com.smilegate.Easel.data.refreshTimelineData
import com.smilegate.Easel.databinding.FragmentProfileBinding
import com.smilegate.Easel.presentation.adapter.TimelineRecyclerViewAdapter
import kotlin.math.abs

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var recyclerViewAdapter: TimelineRecyclerViewAdapter

    private lateinit var navController: NavController

    private var isFabOpen = false
    private lateinit var gestureDetector: GestureDetector
    private lateinit var gestureDetectorTab: GestureDetector

    private lateinit var vibrator: Vibrator

    private var isAnimationRunning = false

    private val tabTitles = listOf("게시물", "답글", "하이라이트", "미디어", "마음에 들어요")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().setTheme(R.style.ProfileTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_container)
        toolbar.visibility = View.GONE

        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigation?.visibility = View.VISIBLE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        val backBtnList = listOf(
            binding.icBack,
            binding.ivBackBtn,
        )

        for (view in backBtnList) {
            view.setOnClickListener {
                navController.navigate(R.id.action_profileFragment_to_timeLineFragment)
            }
        }

        val tabLayout = binding.tabLayout

        tabLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), R.color.Blue_500))

        for (i in tabTitles.indices) {
            tabLayout.addTab(tabLayout.newTab().setText(tabTitles[i]))
        }

        tabLayout.getTabAt(0)?.select()

        val recyclerView = binding.rvProfile

        val currentTabPosition = tabLayout.selectedTabPosition
        val timelineList = ProfileTapRvDataHelper.getDataForTab(currentTabPosition)
        Log.d("TimelineData", timelineList.toString())

        recyclerViewAdapter = TimelineRecyclerViewAdapter(requireContext(), timelineList)

        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewAdapter.notifyDataSetChanged()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // 선택된 탭에 따라 다른 데이터를 가져와서 리사이클러뷰를 업데이트하거나 로딩
                updateRecyclerViewDataForTab(tab?.position ?: 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        updateRecyclerViewDataForTab(0)

        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if(isFabOpen) {
                    toggleFab()
                } else {
                    findNavController().navigate(R.id.action_profileFragment_to_timeLineFragment)
                }
                return@setOnKeyListener true
            }
            false
        }


        vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        setFABClickEvent()

        val swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            refreshTimelineData()
        }

        gestureDetectorTab = GestureDetector(requireContext(), object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                return handleSwipe(e1, e2, velocityX)
            }
        })

        // RecyclerView에 터치 리스너 설정
        recyclerView.setOnTouchListener { _, event ->
            gestureDetectorTab.onTouchEvent(event)
            false
        }

    }

    private fun updateRecyclerViewDataForTab(tabPosition: Int) {
        val dataForTab = ProfileTapRvDataHelper.getDataForTab(tabPosition)
        recyclerViewAdapter.updateData(dataForTab)
    }

    private fun setFABClickEvent() {

        gestureDetector = GestureDetector(requireContext(), object : GestureDetector.SimpleOnGestureListener() {

            override fun onLongPress(e: MotionEvent) {

                binding.overlay.visibility = View.VISIBLE

                if (!isAnimationRunning) {
                    toggleFab()

                    scaleFab(binding.fabMain, 0.8f)
                    setFabAttributes(binding.fabMain, R.drawable.ic_x, R.color.Blue_500)

                    setElevationCompat(binding.fabGif, 15f)
                    setElevationCompat(binding.fabImage, 15f)
                    setElevationCompat(binding.fabWrite, 15f)

                    vibrator.vibrate(100)

                    isAnimationRunning = true
                }
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {

                binding.overlay.visibility = View.GONE

                if(isFabOpen) {
                    toggleFab()

                    scaleFab(binding.fabMain, 1.0f)
                    setFabAttributes(binding.fabMain, R.drawable.ic_add_text, R.color.white)

                    setElevationCompat(binding.fabGif, 0f)
                    setElevationCompat(binding.fabImage, 0f)
                    setElevationCompat(binding.fabWrite, 0f)

                    vibrator.vibrate(100)

                    isAnimationRunning = false
                }
                return true
            }
        })

        binding.fabMain.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }

        binding.root.setOnTouchListener { _, event ->
            // 상호작용을 막는 부분
            return@setOnTouchListener isAnimationRunning
        }

        binding.fabMain.setOnClickListener {
            if(!isFabOpen) {
                findNavController().navigate(R.id.action_profileFragment_to_postFragment)
            }
        }

        binding.fabImage.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_postFragment)
        }

        binding.fabGif.setOnClickListener {
            Toast.makeText(requireContext(), "아직 지원하지 않는 기능입니다", Toast.LENGTH_SHORT).show()
        }

        binding.fabWrite.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_postFragment)
        }
    }

    private fun toggleFab() {
        // 플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션
        if (isFabOpen) {
            moveFab(binding.fabWrite, 0f, 0f)
            moveFab(binding.fabGif, 0f, 0f)
            moveFab(binding.fabImage, 0f, 0f)

            ObjectAnimator.ofFloat(binding.fabMain, View.ROTATION, 180f, 0f).apply { start() }
        } else { // 플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션
            moveFab(binding.fabWrite, 16 * 1.25f, -298 * 1.25f)
            moveFab(binding.fabGif, -170 * 1.25f, -170 * 1.25f)
            moveFab(binding.fabImage, -260 * 1.25f, 30 * 1.25f)

            ObjectAnimator.ofFloat(binding.fabMain, View.ROTATION, 180f, 0f).apply { start() }
        }

        isFabOpen = !isFabOpen

        isAnimationRunning = false

    }

    private fun moveFab(fab: FloatingActionButton, translationX: Float, translationY: Float) {
        ObjectAnimator.ofFloat(fab, "translationX", translationX).apply { start() }
        ObjectAnimator.ofFloat(fab, "translationY", translationY).apply { start() }
    }

    private fun scaleFab(fab: FloatingActionButton, scaleFactor: Float) {
        fab.scaleX = scaleFactor
        fab.scaleY = scaleFactor
    }

    private fun setFabAttributes(fab: FloatingActionButton, imageResource: Int, colorResource: Int) {
        fab.setImageResource(imageResource)
        fab.setColorFilter(
            ContextCompat.getColor(requireContext(), colorResource),
            PorterDuff.Mode.SRC_IN
        )
    }

    private fun setElevationCompat(fab: FloatingActionButton, elevation: Float) {
        fab.setElevationCompat(elevation)
    }
    // 탭 전환 메서드
    private fun navigateToNextTab() {
        val tabLayout = binding.tabLayout

        val currentTab = tabLayout.selectedTabPosition
        tabLayout.getTabAt((currentTab + 1).coerceAtMost(tabTitles.size - 1))?.select()
    }

    private fun navigateToPreviousTab() {
        val tabLayout = binding.tabLayout

        val currentTab = tabLayout.selectedTabPosition
        tabLayout.getTabAt((currentTab - 1).coerceAtLeast(0))?.select()
    }

    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    fun handleSwipe(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float): Boolean {
        if (e1 != null && e2 != null) {
            val deltaX = e2.x - e1.x
            val deltaY = e2.y - e1.y
            return handleHorizontalSwipe(deltaX, deltaY, velocityX)
        }
        return false
    }

    private fun handleHorizontalSwipe(deltaX: Float, deltaY: Float, velocityX: Float): Boolean {
        val absDeltaX = abs(deltaX)
        val absDeltaY = abs(deltaY)

        if (absDeltaX > absDeltaY && absDeltaX > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
            if (deltaX > 0) {
                // 오른쪽으로 스와이프한 경우
                navigateToPreviousTab()
            } else {
                // 왼쪽으로 스와이프한 경우
                navigateToNextTab()
            }
            return true
        }
        return false
    }

}
