package com.smilegate.Easel.presentation.view.join

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentSendCodeBinding
import com.smilegate.Easel.domain.api.ApiService
import com.smilegate.Easel.domain.containsSpaceOrNewline
import com.smilegate.Easel.domain.repository.SendCodeRepository
import com.smilegate.Easel.presentation.viewmodel.JoinViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SendCodeFragment : Fragment() {
    private lateinit var binding: FragmentSendCodeBinding

    private lateinit var navController: NavController

    // Activity 범위에서 공유하는 경우
    private val joinViewModel: JoinViewModel by activityViewModels()
    private lateinit var sendCodeRepository: SendCodeRepository

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

        // Retrofit 인스턴스 생성 및 SendCodeRepository 초기화
        val retrofit = Retrofit.Builder()
            .baseUrl("http://3.37.228.11:8000/")
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환기 추가
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // 모든 통신 로그를 보이도록 설정
            }).build())
            .build()

        sendCodeRepository = SendCodeRepository(retrofit.create(ApiService::class.java))

        binding.sendCodeNextBtn.setOnClickListener {
            val code = binding.sendCodeInputField.text.toString()
            val email = joinViewModel.email.value ?: ""
            if (email.isNotEmpty() && code.isNotEmpty()) {
                //sendCodeRepository.sendCode(email, code)
            } else {
                Toast.makeText(requireContext(), "이메일 또는 코드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.sendCodeRecendEmail.setOnClickListener {
            Toast.makeText(requireContext(), "지원하지 않는 기능입니다.", Toast.LENGTH_SHORT).show()
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
        }

        // LiveData를 사용하여 데이터 변경 감지
        joinViewModel.email.observe(viewLifecycleOwner) { email ->

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
                if (containsSpaceOrNewline(s)) {
                    // 스페이스바 또는 엔터키 입력을 막음
                    s?.delete(s.length - 1, s.length)
                }
                checkEditTextAndEnableButton()
            }
        })

    }

    private fun checkEditTextAndEnableButton() {
        val editText = binding?.root?.findViewById<EditText>(R.id.send_code_input_field)
        val nextButton = binding?.root?.findViewById<Button>(R.id.send_code_next_btn)

        // EditText의 텍스트가 비어있지 않으면 버튼 활성화
        if (!editText?.text.isNullOrEmpty()) {
            nextButton?.isEnabled = true
            binding.sendCodeNextBtn.resources.getResourceName(R.drawable.btn_login_fragment_next)
            // 버튼 텍스트 컬러 설정
            val textColorResourceId = R.color.white
            val textColor = ContextCompat.getColor(requireContext(), textColorResourceId)
            nextButton?.setTextColor(textColor)
        } else {
            nextButton?.isEnabled = false
            binding.sendCodeNextBtn.resources.getResourceName(R.drawable.btn_start_fragment)
            // 버튼 텍스트 컬러 설정
            val textColorResourceId = R.color.Grey_300
            val textColor = ContextCompat.getColor(requireContext(), textColorResourceId)
            nextButton?.setTextColor(textColor)
        }
    }
}
