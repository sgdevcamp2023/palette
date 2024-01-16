package com.smilegate.Easel.ui.join

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smilegate.Easel.databinding.FragmentCreateAccountBinding


class CreateAccountFragment : Fragment() {

    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
