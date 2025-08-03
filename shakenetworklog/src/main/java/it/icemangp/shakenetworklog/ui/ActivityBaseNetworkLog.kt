package it.icemangp.shakenetworklog.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import it.icemangp.shakenetworklog.R
import it.icemangp.shakenetworklog.ui.utils.UiUtils

abstract class ActivityBaseNetworkLog : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView()
//        applyWindowInsets()

//        UiUtils.applyWindowInsets(getMainView(), this.window, R.color.colorPrimaryVariant)
//        UiUtils.setStatusBarColor(this.window, ContextCompat.getColor(this, R.color.colorPrimaryVariant))
    }

    private fun applyWindowInsets() {
        UiUtils.setupStatusBarAppearance(
            rootView = findViewById(R.id.main),
            window = this.window,
            statusBarColorView = findViewById(R.id.statusBarBackground),
            statusBarColorRes = R.color.colorPrimaryVariant
        )
    }

    abstract fun setContentView()
    abstract fun getMainView(): View

}