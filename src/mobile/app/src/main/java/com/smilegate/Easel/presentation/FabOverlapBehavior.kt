package com.smilegate.Easel.presentation

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.github.clans.fab.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class FabOverlapBehavior : CoordinatorLayout.Behavior<FloatingActionButton>() {
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: FloatingActionButton,
        dependency: View
    ): Boolean {
        return dependency is Snackbar.SnackbarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: FloatingActionButton,
        dependency: View
    ): Boolean {
        val translationY = Math.min(0f, dependency.translationY - dependency.height)
        child.translationY = translationY
        return true
    }
}
