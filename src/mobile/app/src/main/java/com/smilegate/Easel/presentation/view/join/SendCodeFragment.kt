package com.smilegate.Easel.presentation.view.join

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentSendCodeBinding
import com.smilegate.Easel.presentation.viewmodel.JoinViewModel

class SendCodeFragment : Fragment() {
    private lateinit var binding: FragmentSendCodeBinding

    private lateinit var navController: NavController

    private val joinViewModel: JoinViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSendCodeBinding.inflate(inflater, container, false)

        navController = findNavController()

        binding.sendCodeNextBtn.setOnClickListener {
            navController.navigate(R.id.action_sendCodeFragment_to_needPasswordFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel에서 데이터 가져와서 UI 업데이트
        binding?.sendCodeEmail?.text = joinViewModel.getEmailValue()

    }

}
