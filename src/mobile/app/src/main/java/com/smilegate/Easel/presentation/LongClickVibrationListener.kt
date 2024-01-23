package com.smilegate.Easel.presentation

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class LongClickVibrationListener(context: Context, private val onLongClick: () -> Unit) :
    View.OnTouchListener {

    private val vibrator: Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    private val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onLongPress(e: MotionEvent) {
            onLongClick.invoke()
            vibrate()
        }
    })

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return event.let { gestureDetector.onTouchEvent(it!!) }
    }

    private fun vibrate() {
        // Check if the device supports vibration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(VIBRATION_DURATION, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(VIBRATION_DURATION)
        }
    }

    companion object {
        private const val VIBRATION_DURATION = 100L // Vibration duration in milliseconds
    }
}
