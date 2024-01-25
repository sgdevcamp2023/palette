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
import com.github.clans.fab.FloatingActionButton
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
        toolbar.visibility = View.VISIBLE

        val profileButton = toolbar.findViewById<ImageView>(R.id.back_btn)
        profileButton.setImageResource(R.drawable.ic_circle_person)
        profileButton.visibility = View.VISIBLE
        profileButton.setOnClickListener {
            findNavController().navigate(R.id.myPageFragment)
        }

        val settingButton = toolbar.findViewById<ImageView>(R.id.else_btn)
        settingButton.setImageResource(R.drawable.ic_setting)
        settingButton.visibility = View.VISIBLE

        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigation?.visibility = View.VISIBLE

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
            if (keyCode == KeyEvent.KEYCODE_BACK && isCurrentFragment()) {
                handleBackPressed()
                return@setOnKeyListener true
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
                findNavController().navigate(R.id.action_noticeFragment_to_postFragment)
            }
        }

        binding.fabImage.setOnClickListener {
            findNavController().navigate(R.id.action_noticeFragment_to_postFragment)
        }

        binding.fabGif.setOnClickListener {
            Toast.makeText(requireContext(), "아직 지원하지 않는 기능입니다", Toast.LENGTH_SHORT).show()
//            val message = "Custom Toast with Background Color"
//            val backgroundColor = Color.parseColor("#95FFFFFF")
//            val customToast = createCustomToast(requireContext(), message, backgroundColor)
//            customToast.show()
        }

        binding.fabWrite.setOnClickListener {
            findNavController().navigate(R.id.action_noticeFragment_to_postFragment)
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

    private fun handleBackPressed() {
        if (doubleBackPressed) {
            requireActivity().finish()
            return
        }

        doubleBackPressed = true
        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackPressed = false
        }, 2000)
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
