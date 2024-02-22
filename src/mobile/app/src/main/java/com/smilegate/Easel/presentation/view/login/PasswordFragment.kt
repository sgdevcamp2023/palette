package com.smilegate.Easel.presentation.view.login

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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.R
import com.smilegate.Easel.data.TokenManager
import com.smilegate.Easel.databinding.FragmentPasswordBinding
import com.smilegate.Easel.domain.api.ApiService
import com.smilegate.Easel.domain.containsSpaceOrNewline
import com.smilegate.Easel.domain.model.login.LoginRequest
import com.smilegate.Easel.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PasswordFragment : Fragment() {
    private var _binding: FragmentPasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private var passwordVisible = false

    // Activity 범위에서 공유
    private val loginViewModel: LoginViewModel by activityViewModels()
    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordBinding.inflate(inflater, container, false)

        navController = findNavController()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://3.37.228.11:8000/")
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환기 추가
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // 모든 통신 로그를 보이도록 설정
            }).build())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        binding.passwordFragmentLostPw.setOnClickListener {
            navController.navigate(R.id.action_passwordFragment_to_FindAccountFragment)
        }

        binding.passwordFragmentLoginBtn.setOnClickListener {

            val email = loginViewModel.email.value // 이메일 가져오기
            val password = binding.passwordFragmentPwField.text.toString()
            // 비밀번호 가져오기

            if (email != null && password != null) { // 이메일과 비밀번호가 모두 null이 아닌지 확인
                // 로그인 요청
                login(email, password)
                hideKeyboard()
            }
            else {
                Toast.makeText(requireContext(), "이메일 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                hideKeyboard()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("PasswordFragment", "onViewCreated")

        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_container)
        val backButton = toolbar.findViewById<ImageView>(R.id.back_btn)
        backButton.visibility = View.VISIBLE

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        val settingButton = toolbar.findViewById<ImageView>(R.id.else_btn)
        settingButton.setImageResource(R.drawable.ic_setting)
        settingButton.visibility = View.GONE

         //LiveData를 사용하여 데이터 변경 감지
        loginViewModel.email.observe(viewLifecycleOwner) { email ->
            Log.d("PasswordFragment", "email value retrieved: $email")

            // id를 사용하여 UI 업데이트 등을 수행
            binding?.passwordFragmentIdField?.text = email
        }

        binding.passwordFragmentPwShowBtn.setOnClickListener {
            passwordVisibility()
        }

        binding.passwordFragmentPwField.addTextChangedListener(object : TextWatcher {
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

    private fun checkEditTextAndEnableButton() {
        val editText = binding?.root?.findViewById<EditText>(R.id.password_fragment_pw_field)
        val nextButton = binding?.root?.findViewById<Button>(R.id.password_fragment_login_btn)

        // EditText의 텍스트가 비어있지 않으면 버튼 활성화
        if (!editText?.text.isNullOrEmpty()) {
            nextButton?.isEnabled = true
            binding.passwordFragmentLoginBtn.resources.getResourceName(R.drawable.btn_login_fragment_next)
            // 버튼 텍스트 컬러 설정
            val textColorResourceId = R.color.white
            val textColor = ContextCompat.getColor(requireContext(), textColorResourceId)
            nextButton?.setTextColor(textColor)
        } else {
            nextButton?.isEnabled = false
            binding.passwordFragmentLoginBtn.resources.getResourceName(R.drawable.btn_start_fragment)
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

    private fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        lifecycleScope.launch {
            try {
                val response = apiService.login(loginRequest)
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        TokenManager.saveAccessToken(requireContext(), loginResponse.accessToken)
                        TokenManager.saveRefreshToken(requireContext(), loginResponse.refreshToken)

                        navController.navigate(R.id.action_passwordFragment_to_timelineFragment)
                    }
                } else {
                    // 로그인 실패
                    Toast.makeText(requireContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // 네트워크 오류 등 예외 발생 시 처리
                Log.e("LoginFragment", "Error: ${e.message}", e)
                Toast.makeText(requireContext(), "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
