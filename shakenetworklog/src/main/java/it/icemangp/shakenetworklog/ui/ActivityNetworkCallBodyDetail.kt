package it.icemangp.shakenetworklog.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.appbar.MaterialToolbar
import it.icemangp.shakenetworklog.R
import it.icemangp.shakenetworklog.data.NetworkLogManager
import it.icemangp.shakenetworklog.ui.utils.JsonUtils.tryFormattingJson
import it.icemangp.shakenetworklog.ui.utils.UiUtils

class ActivityNetworkCallBodyDetail : AppCompatActivity() {

    companion object {
        const val NETWORK_CALL_ID = "ActivityNetworkCallDetail_NETWORK_CALL_ID"
        const val BODY_TYPE = "ActivityNetworkCallDetail_BODY_TYPE"
        const val REQUEST = "ActivityNetworkCallDetail_REQUEST"
        const val RESPONSE = "ActivityNetworkCallDetail_RESPONSE"
    }

    private lateinit var body: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_network_call_body_detail)

        val toolbar = findViewById<MaterialToolbar>(R.id.mytoolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
            title = getString(R.string.snl_lib_name)
        }

        UiUtils.setupStatusBarAppearance(
            rootView = findViewById(R.id.scrollView),
            window = this.window,
            statusBarColorView = findViewById(R.id.statusBarBackground),
            statusBarColorRes = R.color.colorPrimary
        )

        val networkCallId = intent.getStringExtra(NETWORK_CALL_ID) ?: throw IllegalArgumentException("")
        val bodyType = intent.getStringExtra(BODY_TYPE) ?: throw IllegalArgumentException("")
        val networkCall = NetworkLogManager.findCallWithId(networkCallId)

        body = when (bodyType) {
            REQUEST -> networkCall?.requestBody.orEmpty()
            RESPONSE -> networkCall?.responseBody.orEmpty()
            else -> ""
        }

        initTextView()
    }

    private fun initTextView() {
        if (body.isEmpty()) {
            setEmptyBodyVisibility(true)
        } else {
            val jsonString = tryFormattingJson(body)
            setEmptyBodyVisibility(jsonString.isNullOrEmpty())
            setBodyText(jsonString)
        }
    }

    private fun setBodyText(text: String) {
        findViewById<TextView>(R.id.networkCallBodyDetailContent).text = text
    }

    private fun setEmptyBodyVisibility(showView: Boolean) {
        findViewById<TextView>(R.id.networkCallBodyDetailContent).isVisible = showView.not()
        findViewById<TextView>(R.id.emptyBodyTextView).isVisible = showView
    }
}