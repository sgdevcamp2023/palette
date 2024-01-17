package com.smilegate.Easel.presentation.view.join

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        binding.sendCodeInputField.addTextChangedListener(object : TextWatcher {
            // 텍스트 변경 전에 호출되는 메소드
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            // 텍스트 변경 중에 호출되는 메소드
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            // 텍스트 변경 후에 호출되는 메소드
            override fun afterTextChanged(s: Editable?) {
                if (s?.contains(" ") == true || s?.contains("\n") == true) {
                    // 스페이스바 또는 엔터키 입력을 막음
                    s.delete(s.length - 1, s.length)
                }
            }
        })

    }

}
