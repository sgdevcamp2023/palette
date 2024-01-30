package com.smilegate.Easel.presentation.view.profile

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Vibrator
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
import com.smilegate.Easel.databinding.FragmentProfileBinding
import com.smilegate.Easel.domain.model.TimelineItem
import com.smilegate.Easel.presentation.adapter.TimelineRecyclerViewAdapter

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var recyclerViewAdapter: TimelineRecyclerViewAdapter

    private lateinit var navController: NavController

    private var isFabOpen = false
    private lateinit var gestureDetector: GestureDetector

    private lateinit var vibrator: Vibrator

    private var isAnimationRunning = false

    private var initialX = 0f
    private val SWIPE_THRESHOLD = 100 // 스와이프 임계값, 조정 가능
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

        val recyclerView = binding.rvProfile

        val timelineList = generateDummyTimelineData()
        recyclerViewAdapter = TimelineRecyclerViewAdapter(requireContext(), timelineList) // 초기화시 데이터는 비어있을 수 있음
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        for (i in tabTitles.indices) {
            tabLayout.addTab(tabLayout.newTab().setText(tabTitles[i]))
        }

        tabLayout.getTabAt(0)?.select()

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

    }

    private fun updateRecyclerViewDataForTab(tabPosition: Int) {
        val dataForTab = getDataForTab(tabPosition)
        recyclerViewAdapter.updateData(dataForTab)
    }

    private fun getDataForTab(tabPosition: Int): List<TimelineItem> {
        //TODO: 각 탭에 맞는 데이터를 가져오는 로직을 구현합니다.
        // 예를 들어, 탭에 따라 다른 데이터를 데이터베이스에서 가져오거나 서버로부터 요청합니다.
        return when (tabPosition) {
            0 -> generateDummyTimelineData() // 게시물
            1 -> generateDummyTimelineData() // 답글
            2 -> generateDummyTimelineData() // 하이라이트
            3 -> generateDummyTimelineData() // 미디어
            4 -> generateDummyTimelineData() // 마음에 들어요
            else -> emptyList() // 기본적으로 빈 리스트 반환
        }
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

    private fun generateDummyTimelineData(): List<TimelineItem> {
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
}
