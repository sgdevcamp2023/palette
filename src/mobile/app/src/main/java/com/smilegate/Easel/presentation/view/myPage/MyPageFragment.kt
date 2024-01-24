package com.smilegate.Easel.presentation.view.myPage

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentMyPageBinding
import com.smilegate.Easel.databinding.FragmentProfileBinding

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

        binding.icCloseBtn.setOnClickListener {
            navController.navigate(R.id.action_myPageFragment_to_TimelineFragment)
        }

        binding.ivProfileIcon.setOnClickListener {
            navController.navigate(R.id.action_myPageFragment_to_profileFragment)
        }

        binding.tvProfileMenu.setOnClickListener {
            navController.navigate(R.id.action_myPageFragment_to_profileFragment)
        }

        binding.icToggleDown.setOnClickListener {
            isSettingExpanded = !isSettingExpanded
            updateSettingVisibility()
        }
    }

    private fun updateSettingVisibility() {
        if (isSettingExpanded) {
            // 설정 메뉴가 열린 상태
            binding.icToggleDown.setImageResource(R.drawable.ic_up_arrow)
            binding.icToggleDown.setColorFilter(R.color.Blue_500)
            ObjectAnimator.ofFloat(binding.icToggleDown, View.ROTATION, 180f, 0f).apply { start() }

            binding.tvSettingMenu.visibility = View.VISIBLE
            binding.icSettingMenu.visibility = View.VISIBLE

            binding.tvCustomerMenu.visibility = View.VISIBLE
            binding.icCustomerMenu.visibility = View.VISIBLE

            binding.tvPerchaseMenu.visibility = View.VISIBLE
            binding.icPerchaseMenu.visibility = View.VISIBLE

        } else {
            // 설정 메뉴가 닫힌 상태
            binding.icToggleDown.setImageResource(R.drawable.ic_down_arrow)
            binding.icToggleDown.setColorFilter(R.color.black)
            ObjectAnimator.ofFloat(binding.icToggleDown, View.ROTATION, 180f, 0f).apply { start() }

            binding.tvSettingMenu.visibility = View.GONE
            binding.icSettingMenu.visibility = View.GONE

            binding.tvCustomerMenu.visibility = View.GONE
            binding.icCustomerMenu.visibility = View.GONE

            binding.tvPerchaseMenu.visibility = View.GONE
            binding.icPerchaseMenu.visibility = View.GONE
        }
    }
}
