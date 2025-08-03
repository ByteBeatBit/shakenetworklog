package it.icemangp.shakenetworklog.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import it.icemangp.shakenetworklog.R
import it.icemangp.shakenetworklog.ui.utils.UiUtils

abstract class ActivityBaseNetworkLog : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutResource())
        setupToolbar()
        setupStatusBarAppearance()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<MaterialToolbar>(R.id.mytoolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
            title = getString(R.string.snl_lib_name)
        }
    }

    private fun setupStatusBarAppearance() {
        UiUtils.setupStatusBarAppearance(
            rootView = findViewById(R.id.main),
            window = this.window,
            statusBarColorView = findViewById(R.id.statusBarBackground),
            statusBarColorRes = R.color.colorPrimary
        )
    }

    @LayoutRes
    abstract fun getLayoutResource(): Int
}