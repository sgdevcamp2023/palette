package com.smilegate.Easel.presentation.view.join

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentNeedPasswordBinding
import com.smilegate.Easel.domain.containsSpaceOrNewline
import com.smilegate.Easel.domain.isDoneAction

class NeedPasswordFragment : Fragment() {
    private lateinit var binding: FragmentNeedPasswordBinding

    private lateinit var navController: NavController

    private var passwordVisible = false
    private val maxPasswordLength = 8

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNeedPasswordBinding.inflate(inflater, container, false)

        navController = findNavController()

        binding.needPasswordFragmentNextBtn.setOnClickListener {
            if (isPasswordValid()) {
                navController.navigate(R.id.action_needPasswordFragment_to_profileImageFragment)
            } else {
                Toast.makeText(requireContext(), "비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.needPasswordFragmentPwShowBtn.setOnClickListener {
            passwordVisibility()
        }

        // 키보드의 "Done" 또는 "Enter" 키를 감지
        binding.needPasswordFragmentPwField.setOnEditorActionListener { _, actionId, event ->
            if (isDoneAction(actionId, event)) {
                validatePassword()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

//        binding.needPasswordFragmentPwField.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
//            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
//                // 뒤로가기 버튼이 눌렸을 때
//                validatePassword()
//                return@OnKeyListener true
//            }
//            return@OnKeyListener false
//        })

        binding.needPasswordFragmentPwField.addTextChangedListener(object : TextWatcher {
            // 텍스트 변경 전에 호출되는 메소드
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            // 텍스트 변경 중에 호출되는 메소드
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            // 텍스트 변경 후에 호출되는 메소드
            override fun afterTextChanged(s: Editable?) {
                if (containsSpaceOrNewline(s)) {
                    // 스페이스바 또는 엔터키 입력을 막음
                    s?.delete(s.length - 1, s.length)
                }
                checkEditTextAndEnableButton()
            }
        })

    }

    private fun passwordVisibility() {
        passwordVisible = !passwordVisible

        if (passwordVisible) {
            // 비밀번호 보이기
            binding.needPasswordFragmentPwField.inputType = android.text.InputType.TYPE_CLASS_TEXT
            binding.needPasswordFragmentPwShowBtn.setImageResource(R.drawable.ic_eye_on)
        } else {
            // 비밀번호 감추기
            binding.needPasswordFragmentPwField.inputType =
                android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.needPasswordFragmentPwShowBtn.setImageResource(R.drawable.ic_eye_off)
        }

        // 커서를 맨 끝으로 이동하여 가려진 텍스트를 볼 수 있도록 함
        binding.needPasswordFragmentPwField.setSelection(binding.needPasswordFragmentPwField.text.length)
    }

    private fun checkEditTextAndEnableButton() {
        val editText = binding.needPasswordFragmentPwField
        val nextButton = binding.needPasswordFragmentNextBtn

        if (isPasswordValid()) {
            nextButton.isEnabled = true
            nextButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        } else {
            nextButton.isEnabled = false
            nextButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.Grey_300))
        }
    }

    private fun validatePassword() {
        val password = binding.needPasswordFragmentPwField.text.toString()

        if (password.length < 8) {
            // 비밀번호가 8자 미만인 경우
            val pwColorResourceId = ContextCompat.getColor(requireContext(), R.color.Red_200)
            binding.needPasswordFragmentPwField.setTextColor(pwColorResourceId)
            // 토스트 메시지 표시
            Toast.makeText(requireContext(), "비밀번호는 8자 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
        } else {
            // 비밀번호가 유효한 경우
            val pwColorResourceId = ContextCompat.getColor(requireContext(), R.color.Blue_500)
            binding.needPasswordFragmentPwField.setTextColor(pwColorResourceId)
        }
    }

    private fun isPasswordValid(): Boolean {
        val password = binding.needPasswordFragmentPwField.text.toString()
        return password.length >= maxPasswordLength
    }
}
