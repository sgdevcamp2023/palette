package com.smilegate.Easel.presentation.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.databinding.FragmentFindAccountBinding

class FindAccountFragment : Fragment() {
    private lateinit var binding: FragmentFindAccountBinding

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFindAccountBinding.inflate(inflater, container, false)

        navController = findNavController()

//        binding.loginFragmentNextBtn.setOnClickListener {
//            navController.navigate(R.id.action_loginFragment_to_passwordFragment)
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
