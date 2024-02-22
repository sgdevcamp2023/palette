package com.smilegate.Easel.presentation.view.timeline

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smilegate.Easel.R

class ViewBottomSheetDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.view_bottom_sheet_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hideButton = view.findViewById<Button>(R.id.btn_bottom_sheet_hide)

        hideButton.setOnClickListener {
            dismiss()
        }

//        val TextView = view.findViewById<TextView>(R.id.textView7)
//        val originalString = getString(R.string.announce_bottom_sheet)
//        TextView.text = Html.fromHtml(originalString, Html.FROM_HTML_MODE_LEGACY)
//
//        val underlineStart = originalString.indexOf("<u>")
//        val underlineEnd = originalString.indexOf("</u>")
//        val boldStart = originalString.indexOf("<b>")
//        val boldEnd = originalString.indexOf("</b>")
//
//        val spannableString = SpannableString(originalString)
//
//        if (underlineStart != -1 && underlineEnd != -1) {
//            spannableString.setSpan(
//                ForegroundColorSpan(Color.BLACK),
//                underlineStart + "<u>".length,
//                underlineEnd,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//        }
//
//        if (boldStart != -1 && boldEnd != -1) {
//            spannableString.setSpan(
//                ForegroundColorSpan(Color.BLACK),
//                boldStart + "<b>".length,
//                boldEnd,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//        }
//
//        TextView.text = spannableString

    }

    companion object {
        const val TAG = "BasicBottomModalSheet"
    }
}
