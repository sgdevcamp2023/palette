package com.smilegate.Easel.domain

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo

fun isDoneAction(actionId: Int, event: KeyEvent?): Boolean {
    return actionId == EditorInfo.IME_ACTION_DONE ||
            (event != null && event.action == KeyEvent.ACTION_DOWN &&
                    event.keyCode == KeyEvent.KEYCODE_ENTER)
}

fun containsSpaceOrNewline(s: CharSequence?): Boolean {
    return s?.contains(" ") == true || s?.contains("\n") == true
}
