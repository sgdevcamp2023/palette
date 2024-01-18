package com.smilegate.Easel.presentation.view.join

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentAskNameBinding
import com.smilegate.Easel.databinding.FragmentNeedPasswordBinding
import com.smilegate.Easel.domain.containsSpaceOrNewline

class AskNameFragment : Fragment() {
    private lateinit var binding: FragmentAskNameBinding

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAskNameBinding.inflate(inflater, container, false)

        navController = findNavController()

        binding.askNameFragmentJoinBtn.setOnClickListener {
            navController.navigate(R.id.action_askNameFragment_to_timelineFragment)
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
}
