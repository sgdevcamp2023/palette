package com.smilegate.Easel.presentation.view.post

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentPostBinding

class PostFragment : Fragment() {
    private lateinit var binding: FragmentPostBinding

    private lateinit var navController: NavController

    private var isViewVisible: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPostBinding.inflate(inflater, container, false)

        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_container)
        toolbar.visibility = View.GONE

        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigation?.visibility = View.GONE

        binding.etPostContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty() && !isViewVisible) {
                    binding.ivSpace.visibility = TextView.VISIBLE
                    binding.icSpaceIcon.visibility = TextView.VISIBLE
                    binding.ivCamera.visibility = TextView.VISIBLE
                    binding.icCameraIcon.visibility = TextView.VISIBLE
                    binding.tvTempStorage.visibility = TextView.VISIBLE
                    isViewVisible = true
                } else if (!s.isNullOrEmpty() && isViewVisible) {
                    binding.ivSpace.visibility = TextView.INVISIBLE
                    binding.icSpaceIcon.visibility = TextView.INVISIBLE
                    binding.ivCamera.visibility = TextView.INVISIBLE
                    binding.icCameraIcon.visibility = TextView.INVISIBLE
                    binding.tvTempStorage.visibility = TextView.INVISIBLE
                    isViewVisible = false
                }
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        val editText = view.findViewById<EditText>(R.id.et_post_content)
        editText.requestFocus()

        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
}
