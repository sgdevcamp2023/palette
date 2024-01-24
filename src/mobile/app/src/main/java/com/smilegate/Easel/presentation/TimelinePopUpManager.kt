package com.smilegate.Easel.presentation

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.smilegate.Easel.R
import java.util.function.Consumer


class TimelinePopupManager(private val context: Context) {

    private var popupWindow: PopupWindow? = null

    private val mBackgroundBlurRadius = 150
    private val mBackgroundDrawableWithBlur: Drawable? = null
    private val mBackgroundDrawableNoBlur: Drawable? = null

    private val mBlurBehindRadius = 50
    private val mDimAmountWithBlur = 0.1f
    private val mDimAmountNoBlur = 0.6f

    private val blurEnabledListener = Consumer<Boolean> { enabled ->
        val window = (context as? Activity)?.window
        window?.setBackgroundDrawable(if (enabled) mBackgroundDrawableWithBlur else mBackgroundDrawableNoBlur)
        window?.setDimAmount(if (enabled) mDimAmountWithBlur else mDimAmountNoBlur)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun showPopupMenu(anchorView: View) {
        dismissPopupMenu() // 기존 팝업이 열려 있다면 닫기

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customView = inflater.inflate(R.layout.item_timeline_popup, null)

        popupWindow = PopupWindow(
            customView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        applyBlurEffect() // showAsDropDown 이전에 블러 효과를 적용

        val noInterestTextView = customView.findViewById<TextView>(R.id.tv_no_interest)
        noInterestTextView.setOnClickListener {
            dismissPopupMenu()
        }

        popupWindow?.showAsDropDown(anchorView)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun applyBlurEffect() {
        val window = (context as? Activity)?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
        window?.attributes?.blurBehindRadius = mBlurBehindRadius
        window?.setBackgroundBlurRadius(mBackgroundBlurRadius)
        window?.decorView?.addOnAttachStateChangeListener(
            object : View.OnAttachStateChangeListener {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onViewAttachedToWindow(v: View) {
                    window.windowManager.addCrossWindowBlurEnabledListener(blurEnabledListener)
                }

                @RequiresApi(Build.VERSION_CODES.S)
                override fun onViewDetachedFromWindow(v: View) {
                    window.windowManager.removeCrossWindowBlurEnabledListener(blurEnabledListener)
                }
            }
        )
    }

    fun dismissPopupMenu() {
        popupWindow?.dismiss()
        popupWindow = null
    }
}
