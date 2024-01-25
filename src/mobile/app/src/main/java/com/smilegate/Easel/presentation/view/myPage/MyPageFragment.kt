package com.smilegate.Easel.presentation.view.myPage

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding

    private lateinit var navController: NavController

    private var isSettingExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMyPageBinding.inflate(inflater, container, false)

        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_container)
        toolbar.visibility = View.GONE

        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigation?.visibility = View.GONE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        val navigateTimeLine = listOf(
            binding.ivProfileIcon,
            binding.tvProfileMenu,
            binding.tvProfileMenu,
        )

        for (view in navigateTimeLine) {
            view.setOnClickListener {
                navController.navigate(R.id.action_myPageFragment_to_profileFragment)
            }
        }

        val navigateBookMark = listOf(
            binding.ivBookmarkIcon,
            binding.tvBookmarkMenu,
        )

        for (view in navigateBookMark) {
            view.setOnClickListener {
                navController.navigate(R.id.action_myPageFragment_to_bookMarkFragment)
            }
        }

        binding.icCloseBtn.setOnClickListener {
            navController.navigate(R.id.action_myPageFragment_to_TimelineFragment)
        }

        binding.icToggleDown.setOnClickListener {
            isSettingExpanded = !isSettingExpanded
            updateSettingVisibility()
        }

        val clickableViews = listOf(
            binding.icLightMode,
            binding.tvCustomerMenu, binding.icCustomerMenu,
            binding.tvListMenu, binding.ivListIcon,
            binding.tvSpaceMenu, binding.ivSpaceIcon,
            binding.tvMoneyMenu, binding.ivMoneyIcon,
            binding.tvPerchaseMenu, binding.icPerchaseMenu,
        )

        for (view in clickableViews) {
            view.setOnClickListener {
                Toast.makeText(requireContext(), "아직 지원하지 않는 기능입니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateSettingVisibility() {
        if (isSettingExpanded) {
            // 설정 메뉴가 열린 상태
            binding.icToggleDown.setImageResource(R.drawable.ic_up_arrow)
            ObjectAnimator.ofFloat(binding.icToggleDown, View.ROTATION, 180f, 0f).apply { start() }

            val clickableViews = listOf(
                binding.tvSettingMenu,
                binding.icSettingMenu,
                binding.tvCustomerMenu,
                binding.icCustomerMenu,
                binding.tvPerchaseMenu,
                binding.icPerchaseMenu,
            )

            for (view in clickableViews) {
                view.visibility = View.VISIBLE
            }

        } else {
            // 설정 메뉴가 닫힌 상태
            binding.icToggleDown.setImageResource(R.drawable.ic_down_arrow)
            ObjectAnimator.ofFloat(binding.icToggleDown, View.ROTATION, 180f, 0f).apply { start() }

            val clickableViews = listOf(
                binding.tvSettingMenu,
                binding.icSettingMenu,
                binding.tvCustomerMenu,
                binding.icCustomerMenu,
                binding.tvPerchaseMenu,
                binding.icPerchaseMenu,
            )

            for (view in clickableViews) {
                view.visibility = View.GONE
            }
        }
    }
}
