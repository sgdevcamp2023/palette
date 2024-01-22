package com.smilegate.Easel.presentation.view.Timeline

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smilegate.Easel.R

class TimelineBottomSheetDialog : BottomSheetDialogFragment() {

    interface BottomSheetListener {
        fun onRetweetClicked()
        fun onShareClicked()
    }

    private var bottomSheetListener: BottomSheetListener? = null

    fun setBottomSheetListener(listener: BottomSheetListener) {
        bottomSheetListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timeline_bottom_sheet_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.ic_timeline_retweet)?.setOnClickListener {
            bottomSheetListener?.onRetweetClicked()
            dismiss()
        }

        view.findViewById<View>(R.id.ic_timeline_views)?.setOnClickListener {
            bottomSheetListener?.onShareClicked()
            updateViewsDialog()
            dismiss()
        }

        val bottomSheetView = layoutInflater.inflate(R.layout.fragment_timeline_bottom_sheet_dialog, null)
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)
    }

    private fun updateViewsDialog() {
        val icLink = requireView().findViewById<ImageView>(R.id.ic_bottom_sheet_retweet)
        val tvLink = requireView().findViewById<TextView>(R.id.tv_bottom_sheet_retweet)

        val icShare = requireView().findViewById<ImageView>(R.id.ic_bottom_sheet_repost)
        val tvShare = requireView().findViewById<TextView>(R.id.tv_bottom_sheet_repost)

        tvLink.text = "링크복사"
        icLink.setImageResource(R.drawable.ic_clip)

        tvShare.text = "공유하기"
        icShare.setImageResource(R.drawable.ic_share)
    }
}
