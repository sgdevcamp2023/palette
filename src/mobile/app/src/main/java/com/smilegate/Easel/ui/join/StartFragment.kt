package com.smilegate.Easel.ui.join

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentStartBinding

/**
 * A simple [Fragment] subclass.
 * Use the [StartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartFragment : Fragment() {
    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
