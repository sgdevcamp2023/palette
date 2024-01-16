package com.smilegate.Easel.presentation.view.join

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentProfileImageBinding
import com.smilegate.Easel.databinding.FragmentStartBinding

class ProfileImageFragment : Fragment() {
    private lateinit var binding: FragmentProfileImageBinding

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileImageBinding.inflate(inflater, container, false)

        navController = findNavController()

        binding.profileImageFragmentNextBtn.setOnClickListener {
            navController.navigate(R.id.action_profileImageFragment_to_askNameFragment)
        }

        binding.profileImageFragmentSkipBtn.setOnClickListener {
            navController.navigate(R.id.action_profileImageFragment_to_askNameFragment)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
