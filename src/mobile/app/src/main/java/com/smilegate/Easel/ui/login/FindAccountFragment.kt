package com.smilegate.Easel.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentFindAccountBinding
import com.smilegate.Easel.databinding.FragmentLoginBinding

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
