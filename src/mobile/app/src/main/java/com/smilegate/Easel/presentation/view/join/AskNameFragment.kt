package com.smilegate.Easel.presentation.view.join

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentAskNameBinding
import com.smilegate.Easel.databinding.FragmentNeedPasswordBinding

class AskNameFragment : Fragment() {
    private lateinit var binding: FragmentAskNameBinding

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAskNameBinding.inflate(inflater, container, false)

        navController = findNavController()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
