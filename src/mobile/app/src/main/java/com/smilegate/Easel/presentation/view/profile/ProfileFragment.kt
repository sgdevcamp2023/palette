package com.smilegate.Easel.presentation.view.profile

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentProfileBinding
import com.smilegate.Easel.databinding.FragmentProfileImageBinding
import com.smilegate.Easel.databinding.FragmentStartBinding
import com.smilegate.Easel.presentation.adapter.TimelineAdapter
import com.smilegate.Easel.presentation.view.timeline.FollowingFragment
import com.smilegate.Easel.presentation.view.timeline.ForYouFragment

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var navController: NavController

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

        val backButton = activity?.findViewById<ImageView>(R.id.iv_back_btn)
        backButton?.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_timeLineFragment)
        }

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
}
