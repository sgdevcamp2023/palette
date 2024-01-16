package com.smilegate.Easel.presentation.view.join

import android.os.Bundle
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
