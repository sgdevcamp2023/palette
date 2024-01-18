package com.smilegate.Easel.presentation.view.join

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentCreateAccountBinding
import com.smilegate.Easel.domain.containsSpaceOrNewline
import com.smilegate.Easel.presentation.viewmodel.JoinViewModel


class CreateAccountFragment : Fragment() {

    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    // 찾을 대상 텍스트 리스트
    private val targetTexts = listOf("쿠키 사용", "이용 약관", "개인정보 처리방침", "자세히", "알아보기", "여기")

    // Activity 범위에서 공유
    private val joinViewModel: JoinViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)

        navController = findNavController()

        binding?.createAccountBtn?.setOnClickListener {
            val inputText = binding?.createAccountInfoField?.text.toString()

            // ViewModel에 데이터 저장
            joinViewModel.setEmailValue(inputText)

            navController.navigate(R.id.action_createAccountFragment_to_sendCodeFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createAccountInfoField.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateEmail()
            }
        }

        binding.createAccountInfoField.setOnEditorActionListener { _, _, _ ->
            validateEmail()
            true
        }

        val blue = ContextCompat.getColor(requireContext(), R.color.Blue_500)

        // 포커스가 변경될 때 아이콘을 표시
        binding.createAccountNameField.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.createAccountFinishName.visibility = View.INVISIBLE
            } else {
                binding.createAccountNameField.setTextColor(blue)
                binding.createAccountFinishName.visibility = View.VISIBLE
            }
        }


        // createAccountNameField 대한 TextWatcher 설정
        binding.createAccountNameField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (containsSpaceOrNewline(s)) {
                    // 스페이스바 또는 엔터키 입력을 막음
                    s?.delete(s.length - 1, s.length)
                }
            }
        })

        // createAccountInfoField 대한 TextWatcher 설정
        binding.createAccountInfoField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (containsSpaceOrNewline(s)) {
                    // 스페이스바 또는 엔터키 입력을 막음
                    s?.delete(s.length - 1, s.length)
                }
            }
        })

        // TextView에서 텍스트 가져오기
        val fullText = binding?.textView4?.text.toString()

        // SpannableString을 사용하여 색상 변경 및 클릭 이벤트 처리
        val spannableString = SpannableString(fullText)

        for (targetText in targetTexts) {
            val startIndex = fullText.indexOf(targetText)
            if (startIndex != -1) {
                val endIndex = startIndex + targetText.length
                val color = ContextCompat.getColor(requireContext(), R.color.Blue_500)

                // 클릭 이벤트 리스너 설정
                spannableString.setSpan(
                    object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            // 클릭된 텍스트 처리
                            handleTextClick(targetText)
                        }

                        override fun updateDrawState(ds: TextPaint) {
                            super.updateDrawState(ds)
                            ds.color = color
                            ds.bgColor = Color.TRANSPARENT
                            ds.isUnderlineText = false // 밑줄 제거
                        }
                    },
                    startIndex, endIndex, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        // TextView에 SpannableString 설정
        binding?.textView4?.text = spannableString
        // TextView에 ClickableSpan을 적용할 때 clickable을 true로 설정해야 클릭 이벤트가 동작합니다.
        binding?.textView4?.movementMethod = LinkMovementMethod.getInstance()

    }

    private fun handleTextClick(clickedText: String) {
        // 클릭된 텍스트에 따라 동작 수행
        if (clickedText in targetTexts) {
            // "지원하지 않는 기능입니다" 토스트 띄우기 (2초 동안)
            Toast.makeText(requireContext(), "지원하지 않는 기능입니다.", Toast.LENGTH_SHORT).apply {
                duration = Toast.LENGTH_SHORT
            }.show()
        }
    }

    private fun validateEmail() {
        val email = binding.createAccountInfoField.text.toString().trim()
        val blue = ContextCompat.getColor(requireContext(), R.color.Blue_500)
        val red = ContextCompat.getColor(requireContext(), R.color.Red_200)

        if (!isEmailValid(email)) {
            // 이메일 형식이 유효하지 않은 경우
            binding.createAccountInfoField.setTextColor(red)
            binding.createAccountFinishInfo.visibility = View.INVISIBLE

            // 토스트 메시지 표시
            Toast.makeText(requireContext(), "유효한 이메일을 입력하세요.", Toast.LENGTH_SHORT).show()
        } else {
            // 이메일 형식이 유효한 경우
            binding.createAccountInfoField.setTextColor(blue)
            binding.createAccountFinishInfo.visibility = View.VISIBLE
        }
        checkButtonActivation()
    }

    private fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkButtonActivation() {
        val isNameFinished = binding.createAccountFinishName.visibility == View.VISIBLE
        val isInfoFinished = binding.createAccountFinishInfo.visibility == View.VISIBLE

        // 둘 다 완료되었을 때 버튼 활성화
        binding.createAccountBtn.isEnabled = isNameFinished && isInfoFinished
    }
}
