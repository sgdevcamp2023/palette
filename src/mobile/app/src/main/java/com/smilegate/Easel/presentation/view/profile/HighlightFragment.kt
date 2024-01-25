package com.smilegate.Easel.presentation.view.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smilegate.Easel.MainActivity
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentHighlightBinding
import com.smilegate.Easel.databinding.FragmentReplyBinding
import com.smilegate.Easel.domain.model.TimelineItem
import com.smilegate.Easel.presentation.adapter.TimelineRecyclerViewAdapter

class HighlightFragment : Fragment() {
    private lateinit var binding: FragmentHighlightBinding

    private lateinit var navController: NavController

    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHighlightBinding.inflate(inflater, container, false)

        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_container)
        toolbar.visibility = View.GONE

        navController = findNavController()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // MainActivity의 참조를 가져옴
        if (context is MainActivity) {
            mainActivity = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
