package com.smilegate.Easel.presentation.view.myPage

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
    }
}
