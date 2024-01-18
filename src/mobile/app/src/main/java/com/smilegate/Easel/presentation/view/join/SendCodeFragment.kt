package com.smilegate.Easel.presentation.view.join

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentSendCodeBinding
import com.smilegate.Easel.presentation.viewmodel.JoinViewModel

class SendCodeFragment : Fragment() {
    private lateinit var binding: FragmentSendCodeBinding

    private lateinit var navController: NavController

    // Activity 범위에서 공유하는 경우
    private val joinViewModel: JoinViewModel by activityViewModels()

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

        binding.sendCodeRecendEmail.setOnClickListener {
            Toast.makeText(requireContext(), "지원하지 않는 기능입니다.", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                if (s?.contains(" ") == true || s?.contains("\n") == true) {
                    // 스페이스바 또는 엔터키 입력을 막음
                    s.delete(s.length - 1, s.length)
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
