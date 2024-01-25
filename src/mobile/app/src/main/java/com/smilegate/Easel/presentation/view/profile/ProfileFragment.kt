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
import androidx.viewpager.widget.ViewPager
import com.github.clans.fab.FloatingActionButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentProfileBinding
import com.smilegate.Easel.presentation.adapter.TimelineAdapter

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var navController: NavController

    private var isFabOpen = false
    private lateinit var gestureDetector: GestureDetector

    private lateinit var vibrator: Vibrator

    private var isAnimationRunning = false

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

        val viewPager: ViewPager = binding.root.findViewById(R.id.profile_view_pager)
        setupViewPager(viewPager)

        val tabLayout: TabLayout = binding.root.findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), R.color.Blue_500))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

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

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = TimelineAdapter(childFragmentManager)
        adapter.addFragment(MyPostFragment(), "게시물")
        adapter.addFragment(ReplyFragment(), "답글")
        adapter.addFragment(HighlightFragment(), "하이라이트")
        adapter.addFragment(MediaFragment(), "미디어")
        adapter.addFragment(LikedFragment(), "마음에 들어요")
        viewPager.adapter = adapter
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
                findNavController().navigate(R.id.action_timelineFragment_to_postFragment)
            }
        }

        binding.fabImage.setOnClickListener {
            findNavController().navigate(R.id.action_timelineFragment_to_postFragment)
        }

        binding.fabGif.setOnClickListener {
            Toast.makeText(requireContext(), "아직 지원하지 않는 기능입니다", Toast.LENGTH_SHORT).show()
        }

        binding.fabWrite.setOnClickListener {
            findNavController().navigate(R.id.action_timelineFragment_to_postFragment)
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
}
