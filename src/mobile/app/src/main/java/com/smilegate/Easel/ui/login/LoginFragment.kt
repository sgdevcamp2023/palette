package com.smilegate.Easel.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentLoginBinding
import com.smilegate.Easel.databinding.FragmentStartBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        navController = findNavController()

        binding.loginFragmentNextBtn.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_passwordFragment)
        }

        binding.loginFragmentLostPw.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_findAccountFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
