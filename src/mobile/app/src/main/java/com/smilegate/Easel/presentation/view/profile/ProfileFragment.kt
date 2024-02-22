package com.smilegate.Easel.presentation.view.profile

import android.animation.ObjectAnimator
import android.content.ContentValues.TAG
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.clans.fab.FloatingActionButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.smilegate.Easel.R
import com.smilegate.Easel.data.ProfileTapRvDataHelper
import com.smilegate.Easel.data.TokenManager
import com.smilegate.Easel.data.refreshTimelineData
import com.smilegate.Easel.databinding.FragmentProfileBinding
import com.smilegate.Easel.domain.api.ApiService
import com.smilegate.Easel.domain.model.user.UserProfileResponse
import com.smilegate.Easel.presentation.adapter.TimelineRecyclerViewAdapter
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.abs

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var recyclerViewAdapter: TimelineRecyclerViewAdapter

    private lateinit var navController: NavController

    private lateinit var apiService: ApiService
    private lateinit var tokenManager: TokenManager

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

        // Retrofit 인스턴스 초기화
        val retrofit = Retrofit.Builder()
            .baseUrl("http://3.37.228.11:8000/")
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환기 추가
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // 모든 통신 로그를 보이도록 설정
            }).build())
            .build()

        apiService = retrofit.create(ApiService::class.java)
        tokenManager = TokenManager

        // API 호출을 실행하여 회원 정보 가져오기
        getUserProfile()

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

        binding.btnEditProfile.setOnClickListener {
            Toast.makeText(requireContext(), "아직 지원하지 않는 기능입니다.", Toast.LENGTH_SHORT).show()
        }

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

        if (absDeltaX > absDeltaY && absDeltaX > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
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

    private fun getUserProfile() {
        // CoroutineScope에서 API 호출 실행
        lifecycleScope.launch {
            try {
                // 토큰 관리 클래스에서 액세스 토큰 가져오기
                val accessToken = TokenManager.getAccessToken(requireContext())

                // API 호출 실행 및 헤더에 토큰 추가
                val response = apiService.getUserProfile("Bearer $accessToken")
                println("accessToken = ${accessToken}")

                if (response.isSuccessful)  {
                    // API 호출 성공 시 회원 정보 가져오기
                    println("성공직전")
                    val userProfile = response.body()
                    println("성공")
                    // UI 업데이트
                    updateUI(userProfile)
                    println("된건가")
                } else {
                    // API 호출 실패 시 에러 처리
                    Log.e(TAG, "Failed to fetch user profile: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching user profile: ${e.message}", e)
            }
        }
    }

    private fun updateUI(userProfile: UserProfileResponse?) {
        userProfile?.let { profile ->
            if (userProfile.profileImagePath != null) {
                Glide.with(requireContext())
                    .load(userProfile.profileImagePath)
                    .placeholder(R.drawable.sample_profile_img1) // 기본 이미지 설정
                    .error(R.drawable.sample_profile_img1) // 에러 발생 시 기본 이미지 설정
                    .into(binding.circleImageView)
            }
            // 닉네임 설정
            binding.tvUserName.text = "@${profile.username}"

            // 사용자 이름 설정
            binding.tvNickName.text = profile.nickname

            // 사용자 소개 설정
            if (userProfile.introduce != "") {
                binding.tvUserBio.text = profile.introduce
            }
            else {
                binding.tvUserBio.text = "안녕하세요:)"
            }

            // 가입일 설정 (예시 코드)
            val joinedAtString = profile.joinedAt // 서버에서는 문자열로 전달됨
            val joinedAt = parseISO8601(joinedAtString)
            val dateFormat = DateTimeFormatter.ofPattern("yyyy년 MM월에 가입함", Locale.getDefault())
            binding.tvUserJoinDate.text = dateFormat.format(joinedAt)

            // 팔로잉 및 팔로워 수 설정
            binding.tvFollowingNum.text = profile.followingCount.toString()
            binding.tvFollowerNum.text = profile.followerCount.toString()
        }
    }

    // ISO 8601 형식의 날짜를 LocalDateTime 객체로 파싱하는 함수
    fun parseISO8601(dateString: String): LocalDateTime {
        return LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME)
    }

}
