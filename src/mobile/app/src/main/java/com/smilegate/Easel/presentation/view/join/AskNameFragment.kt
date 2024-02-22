package com.smilegate.Easel.presentation.view.join

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentAskNameBinding
import com.smilegate.Easel.domain.api.ApiService
import com.smilegate.Easel.domain.containsSpaceOrNewline
import com.smilegate.Easel.domain.model.join.JoinRequest
import com.smilegate.Easel.domain.model.join.VerifyUsernameRequest
import com.smilegate.Easel.domain.repository.JoinRepository
import com.smilegate.Easel.presentation.viewmodel.JoinViewModel
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AskNameFragment : Fragment() {
    private lateinit var binding: FragmentAskNameBinding

    private lateinit var navController: NavController
    private lateinit var joinViewModel: JoinViewModel

    private lateinit var apiService: ApiService
    private lateinit var joinRepository: JoinRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAskNameBinding.inflate(inflater, container, false)

        navController = findNavController()

        // Retrofit 인스턴스 생성
        val retrofit = Retrofit.Builder()
            .baseUrl("http://3.37.228.11:8000/")
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환기 추가
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // 모든 통신 로그를 보이도록 설정
            }).build())
            .build()

        // ApiService 인터페이스의 구현체 생성
        apiService = retrofit.create(ApiService::class.java)
        joinRepository = JoinRepository(apiService)
        joinViewModel = ViewModelProvider(requireActivity()).get(JoinViewModel::class.java)

        binding.askNameFragmentJoinBtn.setOnClickListener {
            val username = binding.askNameFragmentIdField.text.toString().trim()
            val nickname = joinViewModel.nickname.value.toString()
            val password = joinViewModel.password.value.toString()
            val email = joinViewModel.email.value.toString()
            val profileImageURL = joinViewModel.profileImageURL.value

            if (username.isNotEmpty()) {
                verifyUsername(username)
                // ViewModel에 유저네임 설정
                joinViewModel.setUsernameValue(username)
                navController.navigate(R.id.action_askNameFragment_to_startFragment)
                hideKeyboard()
            } else {
                Toast.makeText(requireContext(), "유저 이메일을 입력하세요", Toast.LENGTH_SHORT).show()
            }

            // 필요한 데이터를 가져와서 JoinRequest 객체를 생성
            val joinRequest = JoinRequest(email, password, username, nickname, profileImageURL)
            joinUser(joinRequest)

            // 서버로 데이터 전송
            (joinRequest)
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

        val settingButton = toolbar.findViewById<ImageView>(R.id.else_btn)
        settingButton.setImageResource(R.drawable.ic_setting)
        settingButton.visibility = View.GONE

        binding.askNameFragmentIdField.addTextChangedListener(object : TextWatcher {
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
        val editText = binding?.root?.findViewById<EditText>(R.id.ask_name_fragment_id_field)
        val nextButton = binding?.root?.findViewById<Button>(R.id.ask_name_fragment_join_btn)

        // EditText의 텍스트가 비어있지 않으면 버튼 활성화
        if (!editText?.text.isNullOrEmpty()) {
            nextButton?.isEnabled = true
            binding.askNameFragmentJoinBtn.resources.getResourceName(R.drawable.btn_login_fragment_next)
            // 버튼 텍스트 컬러 설정
            val textColorResourceId = R.color.white
            val textColor = ContextCompat.getColor(requireContext(), textColorResourceId)
            nextButton?.setTextColor(textColor)
        } else {
            nextButton?.isEnabled = false
            binding.askNameFragmentJoinBtn.resources.getResourceName(R.drawable.btn_start_fragment)
            // 버튼 텍스트 컬러 설정
            val textColorResourceId = R.color.Grey_300
            val textColor = ContextCompat.getColor(requireContext(), textColorResourceId)
            nextButton?.setTextColor(textColor)
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // 현재 포커스된 뷰에서 키패드를 감춥니다.
        val focusedView = requireActivity().currentFocus
        focusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun verifyUsername(username: String) {
        val request = VerifyUsernameRequest(username)
        lifecycleScope.launch {
            try {
                val response = apiService.verifyUsername(request)
                if (response.isSuccessful) {
                    val verifyUsernameResponse = response.body()
                    if (verifyUsernameResponse != null) {
                        if (verifyUsernameResponse.isDuplicated) {
                            Toast.makeText(requireContext(), "중복된 유저네임입니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "회원 가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "서버 오류 발생", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
                Log.e("AskNameFragment", "Error: ${e.message}", e)
            }
        }
    }

    private fun joinUser(joinRequest: JoinRequest) {
        lifecycleScope.launch {
            try {
                val request = JoinRequest(
                    joinRequest.email,
                    joinRequest.password,
                    joinRequest.username,
                    joinRequest.nickname,
                    joinRequest.profileImagePath)
                val response = apiService.joinUser(request)
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    // 가입 성공 후 처리할 작업 추가
                } else {
                    Toast.makeText(requireContext(), "가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
                Log.e("AskNameFragment", "Error: ${e.message}", e)
            }
        }
    }
}
