package com.smilegate.Easel.presentation.view.notice

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import android.view.GestureDetector
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentNoticeBinding

class NoticeFragment : Fragment() {
    private lateinit var binding: FragmentNoticeBinding

    private lateinit var navController: NavController

    private var doubleBackPressed = false

    private var isFabOpen = false
    private lateinit var gestureDetector: GestureDetector

    private lateinit var vibrator: Vibrator

    private var isAnimationRunning = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoticeBinding.inflate(inflater, container, false)

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

        vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        setFABClickEvent()
    }

    private fun isCurrentFragment(): Boolean {
        return true
    }

    private fun setFABClickEvent() {

        gestureDetector = GestureDetector(requireContext(), object : GestureDetector.SimpleOnGestureListener() {

            override fun onLongPress(e: MotionEvent) {

                if (!isAnimationRunning) {
                    toggleFab()

                    binding.fabMain.scaleX = 0.8f
                    binding.fabMain.scaleY = 0.8f

                    binding.fabMain.setImageResource(R.drawable.ic_x)
                    binding.fabMain.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.Blue_500
                        ), PorterDuff.Mode.SRC_IN
                    )

                    binding.fabMain.setElevationCompat(15f)
                    binding.fabGif.setElevationCompat(15f)
                    binding.fabImage.setElevationCompat(15f)
                    binding.fabWrite.setElevationCompat(15f)

                    vibrator.vibrate(100)

                    isAnimationRunning = true
                }
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                if(isFabOpen) {
                    toggleFab()

                    binding.fabMain.scaleX = 1.0f
                    binding.fabMain.scaleY = 1.0f

                    binding.fabMain.setImageResource(R.drawable.ic_add_text)
                    binding.fabMain.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white), PorterDuff.Mode.SRC_IN)

                    binding.fabGif.setElevationCompat(0f)
                    binding.fabImage.setElevationCompat(0f)
                    binding.fabWrite.setElevationCompat(0f)

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
//            val message = "Custom Toast with Background Color"
//            val backgroundColor = Color.parseColor("#95FFFFFF")
//            val customToast = createCustomToast(requireContext(), message, backgroundColor)
//            customToast.show()
        }

        binding.fabWrite.setOnClickListener {
            findNavController().navigate(R.id.action_timelineFragment_to_postFragment)
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

        isAnimationRunning = false

    }
}
