package it.icemangp.shakenetworklog.ui.utils

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

object UiUtils {

    fun setupStatusBarAppearance(
        rootView: View,
        window: Window,
        statusBarColorView: View,
        @ColorRes statusBarColorRes: Int
    ) {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, insets ->
            val statusBarColor = ContextCompat.getColor(view.context, statusBarColorRes)
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                statusBarColorView.setBackgroundColor(statusBarColor)
                statusBarColorView.layoutParams.height = systemInsets.top
                statusBarColorView.requestLayout()

                window.statusBarColor = Color.TRANSPARENT
            } else {
                @Suppress("DEPRECATION")
                window.statusBarColor = statusBarColor
            }

            rootView.setPaddingRelative(
                systemInsets.left,
                0,
                systemInsets.right,
                systemInsets.bottom
            )

            insets
        }
    }
}