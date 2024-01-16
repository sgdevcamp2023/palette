package com.smilegate.Easel.presentation.view.join

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentSendCodeBinding
import com.smilegate.Easel.presentation.viewmodel.JoinViewModel

class SendCodeFragment : Fragment() {
    private lateinit var binding: FragmentSendCodeBinding

    private lateinit var navController: NavController

    // Activity 범위에서 공유하는 경우
    private val joinViewModel: JoinViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("SendCodeFragment", "onCreateView called")
        binding = FragmentSendCodeBinding.inflate(inflater, container, false)

        navController = findNavController()

        binding.sendCodeNextBtn.setOnClickListener {
            navController.navigate(R.id.action_sendCodeFragment_to_needPasswordFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // LiveData를 사용하여 데이터 변경 감지
        joinViewModel.email.observe(viewLifecycleOwner) { email ->
            Log.d("SendCodeFragment", "Email value retrieved: $email")

            // userEmail을 사용하여 UI 업데이트 등을 수행
            binding?.sendCodeEmail?.text = email
        }

    }

}
