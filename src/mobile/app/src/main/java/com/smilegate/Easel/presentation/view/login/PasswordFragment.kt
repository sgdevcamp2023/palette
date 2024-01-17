package com.smilegate.Easel.presentation.view.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentPasswordBinding
import com.smilegate.Easel.presentation.viewmodel.LoginViewModel

class PasswordFragment : Fragment() {
    private lateinit var binding: FragmentPasswordBinding

    private lateinit var navController: NavController

    private var passwordVisible = false

    // Activity 범위에서 공유
    private val loginViewModel: LoginViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordBinding.inflate(inflater, container, false)

        navController = findNavController()

        binding.passwordFragmentLostPw.setOnClickListener {
            navController.navigate(R.id.action_passwordFragment_to_FindAccountFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("LoginFragment", "onViewCreated")

        // LiveData를 사용하여 데이터 변경 감지
        loginViewModel.id.observe(viewLifecycleOwner) { id ->
            Log.d("PasswordFragment", "Id value retrieved: $id")

            // id를 사용하여 UI 업데이트 등을 수행
            binding?.passwordFragmentIdField?.text = id
        }

        binding.passwordFragmentPwShowBtn.setOnClickListener {
            togglePasswordVisibility()
        }

        binding.passwordFragmentPwField.addTextChangedListener(object : TextWatcher {
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

    private fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible

        if (passwordVisible) {
            // 비밀번호 보이기
            binding.passwordFragmentPwField.inputType = android.text.InputType.TYPE_CLASS_TEXT
            binding.passwordFragmentPwShowBtn.setImageResource(R.drawable.ic_eye_on)
        } else {
            // 비밀번호 감추기
            binding.passwordFragmentPwField.inputType =
                android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.passwordFragmentPwShowBtn.setImageResource(R.drawable.ic_eye_off)
        }

        // 커서를 맨 끝으로 이동하여 가려진 텍스트를 볼 수 있도록 함
        binding.passwordFragmentPwField.setSelection(binding.passwordFragmentPwField.text.length)
    }
}
