package com.smilegate.Easel.domain

import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

fun isDoneAction(actionId: Int, event: KeyEvent?): Boolean {
    return actionId == EditorInfo.IME_ACTION_DONE ||
            (event != null && event.action == KeyEvent.ACTION_DOWN &&
                    event.keyCode == KeyEvent.KEYCODE_ENTER)
}

fun containsSpaceOrNewline(s: CharSequence?): Boolean {
    return s?.contains(" ") == true || s?.contains("\n") == true
}


fun handleBackPressed(activity: AppCompatActivity, doubleBackPressed: Boolean): Boolean {
    var doubleBackPressedVar = doubleBackPressed

    if (doubleBackPressedVar) {
        // 두 번째 눌림
        activity.finish()
    } else {
        // 2초 내에 두 번 누르지 않으면 초기화
        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackPressedVar = false
        }, 2000)
    }
    return true
}
