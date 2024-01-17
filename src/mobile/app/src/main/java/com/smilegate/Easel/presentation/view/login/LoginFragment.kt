package com.smilegate.Easel.presentation.view.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentLoginBinding
import com.smilegate.Easel.presentation.viewmodel.JoinViewModel
import com.smilegate.Easel.presentation.viewmodel.LoginViewModel


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private lateinit var navController: NavController

    // Activity 범위에서 공유
    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("LoginFragment", "onCreateView")
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding?.loginFragmentNextBtn?.setOnClickListener {
            val inputText = binding?.loginFragmentInputField?.text.toString()
            Log.d("LoginFragment", "Input text: $inputText")

            // ViewModel에 데이`                                                                                                                       터 저장
            loginViewModel.setIdValue(inputText)
            Log.d("LoginFragment", "Id value set: $inputText")

            navController.navigate(R.id.action_createAccountFragment_to_sendCodeFragment)
        }

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

        binding.loginFragmentInputField.addTextChangedListener(object : TextWatcher {
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
                checkEditTextAndEnableButton()
            }
        })
    }

    private fun checkEditTextAndEnableButton() {
        val editText = binding?.root?.findViewById<EditText>(R.id.login_fragment_input_field)
        val nextButton = binding?.root?.findViewById<Button>(R.id.login_fragment_next_btn)

        // EditText의 텍스트가 비어있지 않으면 버튼 활성화
        if (!editText?.text.isNullOrEmpty()) {
            nextButton?.isEnabled = true
            binding.loginFragmentNextBtn.resources.getResourceName(R.drawable.btn_login_fragment_next)
            // 버튼 텍스트 컬러 설정
            val textColorResourceId = R.color.white
            val textColor = ContextCompat.getColor(requireContext(), textColorResourceId)
            nextButton?.setTextColor(textColor)
        } else {
            nextButton?.isEnabled = false
            binding.loginFragmentNextBtn.resources.getResourceName(R.drawable.btn_start_fragment)
            // 버튼 텍스트 컬러 설정
            val textColorResourceId = R.color.Grey_300
            val textColor = ContextCompat.getColor(requireContext(), textColorResourceId)
            nextButton?.setTextColor(textColor)
        }
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        // 뷰 바인딩 해제
//        binding = null
//    }
}
