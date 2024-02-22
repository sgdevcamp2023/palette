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
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentCreateAccountBinding
import com.smilegate.Easel.domain.api.ApiService
import com.smilegate.Easel.domain.containsSpaceOrNewline
import com.smilegate.Easel.domain.model.TemporaryJoinRequest
import com.smilegate.Easel.domain.repository.SendCodeRepository
import com.smilegate.Easel.domain.repository.UserRepository
import com.smilegate.Easel.presentation.viewmodel.JoinViewModel
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CreateAccountFragment : Fragment() {

    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    // 찾을 대상 텍스트 리스트
    private val targetTexts = listOf("쿠키 사용", "이용 약관", "개인정보 처리방침", "자세히", "알아보기", "여기")

    // Activity 범위에서 공유
    private val joinViewModel: JoinViewModel by activityViewModels()
    private lateinit var sendCodeRepository: SendCodeRepository
    private lateinit var userRepository: UserRepository
    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)

        navController = findNavController()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://3.37.228.11:8000/")
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환기 추가
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // 모든 통신 로그를 보이도록 설정
            }).build())
            .build()

        apiService = retrofit.create(ApiService::class.java)
        userRepository = UserRepository(retrofit.create(ApiService::class.java))
        sendCodeRepository = SendCodeRepository(retrofit.create(ApiService::class.java))

        binding?.createAccountBtn?.setOnClickListener {
            val email = binding?.createAccountInfoField?.text.toString().trim()

            if (isEmailValid(email)) {
                lifecycleScope.launch {
                    try {
                        if (userRepository.verifyEmail(email)) {
                            // 이메일 인증 성공
                            joinViewModel.setEmailValue(email)
                            sendCodeToEmail(email)
                        } else {
                            // 중복된 이메일 처리
                            Toast.makeText(requireContext(), "중복된 이메일입니다.", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        // 네트워크 오류 등의 예외 처리
                        Toast.makeText(requireContext(), "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                        Log.e("CreateAccountFragment", "Error: ${e.message}", e)
                    }
                }
            } else {
                Toast.makeText(requireContext(), "유효한 이메일을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_container)
        val backButton = toolbar.findViewById<ImageView>(R.id.back_btn)
        backButton.visibility = View.VISIBLE

        backButton.setOnClickListener {
            findNavController().navigateUp()
            backButton.visibility = GONE
        }

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
        val red = ContextCompat.getColor(requireContext(), R.color.Pink_200)

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
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailPattern.matches(email)
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

    private fun sendCodeToEmail(email: String) {
        lifecycleScope.launch {
            try {
                // 이메일을 임시 회원가입 API에 요청
                val nickname = "your_nickname_here" // 사용자가 입력한 닉네임을 여기에 할당
                sendTemporaryJoinRequest(email, nickname)

                // 이메일로 코드를 요청
                sendCodeRepository.sendCode(email, "your_payload_here")

                // 코드를 성공적으로 전송한 후, 다음 프래그먼트로 이동
                navController.navigate(R.id.action_createAccountFragment_to_sendCodeFragment)
            } catch (e: Exception) {
                // 오류 처리
                Toast.makeText(requireContext(), "코드를 보내는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                Log.e("CreateAccountFragment", "Error sending code: ${e.message}")
            }
        }
    }

    private suspend fun sendTemporaryJoinRequest(email: String, nickname: String) {
        try {
            val request = TemporaryJoinRequest(email, nickname)
            val response = apiService.temporaryJoin(request)
            if (response.isSuccessful) {
                // 임시 회원가입 요청 성공
                Log.d("CreateAccountFragment", "Temporary join request successful.")
            } else {
                // 요청 실패
                Log.e("CreateAccountFragment", "Failed to send temporary join request. Response code: ${response.code()}")
            }
        } catch (e: Exception) {
            // 네트워크 오류 등 예외 처리
            Log.e("CreateAccountFragment", "Error sending temporary join request: ${e.message}", e)
        }
    }
}
