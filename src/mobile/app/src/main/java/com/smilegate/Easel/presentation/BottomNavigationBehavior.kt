package com.smilegate.Easel.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.view.ViewCompat
import androidx.core.view.ViewCompat.NestedScrollType

class BottomNavigationViewBehavior(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<BottomNavigationView>(context, attrs) {

    private var height = 0

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: BottomNavigationView,
        layoutDirection: Int
    ): Boolean {
        height = child.height
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: BottomNavigationView, directTargetChild: View, target: View,
        axes: Int, type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout, child: BottomNavigationView,
        target: View, dxConsumed: Int, dyConsumed: Int,
        dxUnconsumed: Int, dyUnconsumed: Int,
        @NestedScrollType type: Int
    ) {
        if (dyConsumed > 0) {
            slideDown(child)
        } else if (dyConsumed < 0) {
            slideUp(child)
        }
    }

    private fun slideUp(child: BottomNavigationView) {
        child.clearAnimation()
        child.animate().translationY(0f).duration = 200
    }

    private fun slideDown(child: BottomNavigationView) {
        child.clearAnimation()
        child.animate().translationY(height.toFloat()).duration = 200
    }
}
