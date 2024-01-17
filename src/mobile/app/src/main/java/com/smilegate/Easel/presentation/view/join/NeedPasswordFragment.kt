package com.smilegate.Easel.presentation.view.join

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentNeedPasswordBinding

class NeedPasswordFragment : Fragment() {
    private lateinit var binding: FragmentNeedPasswordBinding

    private lateinit var navController: NavController

    private var passwordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNeedPasswordBinding.inflate(inflater, container, false)

        navController = findNavController()

        binding.needPasswordFragmentNextBtn.setOnClickListener {
            navController.navigate(R.id.action_needPasswordFragment_to_profileImageFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.needPasswordFragmentPwShowBtn.setOnClickListener {
            togglePasswordVisibility()
        }

        binding.needPasswordFragmentPwField.addTextChangedListener(object : TextWatcher {
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

}
