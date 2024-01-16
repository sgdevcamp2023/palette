package com.smilegate.Easel.presentation.view.join

import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentCreateAccountBinding
import com.smilegate.Easel.presentation.viewmodel.JoinViewModel


class CreateAccountFragment : Fragment() {

    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private val joinViewModel: JoinViewModel by viewModels()


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

        // EditText1에 대한 TextWatcher 설정
        binding.createAccountNameField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // 입력이 완료되면 체크 표시 아이콘을 표시
                binding.createAccountFinishName.visibility = if (s?.isNotEmpty() == true) View.VISIBLE else View.INVISIBLE
            }
        })

        // EditText2에 대한 TextWatcher 설정
        binding.createAccountInfoField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // 입력이 완료되면 체크 표시 아이콘을 표시
                binding.createAccountFinishInfo.visibility = if (s?.isNotEmpty() == true) View.VISIBLE else View.INVISIBLE
            }
        })

        // TextView에서 텍스트 가져오기
        val fullText = binding?.textView4?.text.toString()

        // 찾을 대상 텍스트 리스트
        val targetTexts = listOf("쿠키 사용", "이용 약관", "개인정보 처리방침", "자세히", "알아보기", "여기")

        // SpannableString을 사용하여 색상 변경 및 클릭 이벤트 처리
        val spannableString = SpannableString(fullText)

        for (targetText in targetTexts) {
            val startIndex = fullText.indexOf(targetText)
            if (startIndex != -1) {
                val endIndex = startIndex + targetText.length

                // R.color.Blue_500에서 색상을 가져와서 사용
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
                            ds.bgColor = ContextCompat.getColor(requireContext(), android.R.color.transparent)
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
        if (clickedText == "쿠키 사용" || clickedText == "이용 약관" || clickedText == "개인정보 처리방침"
            || clickedText == "자세히" || clickedText == "알아보기" || clickedText == "여기") {
            // "지원하지 않는 기능입니다" 토스트 띄우기 (2초 동안)
            Toast.makeText(requireContext(), "지원하지 않는 기능입니다.", Toast.LENGTH_SHORT).apply {
                duration = Toast.LENGTH_SHORT // 2초 동안 토스트 메시지 띄우기
            }.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
