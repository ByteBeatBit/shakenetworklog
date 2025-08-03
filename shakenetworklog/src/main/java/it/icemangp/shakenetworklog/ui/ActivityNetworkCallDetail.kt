package it.icemangp.shakenetworklog.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import it.icemangp.shakenetworklog.R
import it.icemangp.shakenetworklog.data.ExportType
import it.icemangp.shakenetworklog.data.NetworkCall
import it.icemangp.shakenetworklog.data.NetworkLogManager
import it.icemangp.shakenetworklog.ui.ActivityNetworkCallBodyDetail.Companion.REQUEST
import it.icemangp.shakenetworklog.ui.ActivityNetworkCallBodyDetail.Companion.RESPONSE
import it.icemangp.shakenetworklog.ui.utils.FileUtils.shareExportedContent
import it.icemangp.shakenetworklog.ui.utils.JsonUtils.tryFormattingJson
import it.icemangp.shakenetworklog.ui.utils.StringUtils.toHtmlString
import it.icemangp.shakenetworklog.ui.utils.UiUtils
import java.net.URI

class ActivityNetworkCallDetail : AppCompatActivity() {

    private var networkCall: NetworkCall? = null

    companion object {
        const val NETWORK_CALL_ID = "ActivityNetworkCallDetail_NETWORK_CALL_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_network_call_detail)

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

        val networkCallId = intent.getStringExtra(NETWORK_CALL_ID) ?: throw IllegalArgumentException("NETWORK_CALL_ID cannot be null")
        networkCall = NetworkLogManager.findCallWithId(networkCallId)

        initUi()
    }

    private fun initUi() {
        initPath()
        initOverview()
        initRequestHeaders()
        initResponseHeaders()
        initButtons()
    }

    private fun initButtons() {
        val requestBodyButton = findViewById<MaterialButton>(R.id.requestBodyButton)
        val responseBodyButton = findViewById<MaterialButton>(R.id.responseBodyButton)

        requestBodyButton.apply {
            isEnabled = isEmptyBody(REQUEST).not()
            setOnClickListener {
                openBodyActivity(REQUEST)
            }
        }
        responseBodyButton.apply {
            isEnabled = isEmptyBody(RESPONSE).not()
            setOnClickListener {
                openBodyActivity(RESPONSE)
            }
        }
    }

    private fun isEmptyBody(bodyType: String): Boolean {
        val body = when (bodyType) {
            REQUEST -> networkCall?.requestBody.orEmpty()
            RESPONSE -> networkCall?.responseBody.orEmpty()
            else -> ""
        }
        return body.isEmpty()
    }

    private fun openBodyActivity(type: String) {
        val intent = Intent(this, ActivityNetworkCallBodyDetail::class.java)
        intent.putExtra(ActivityNetworkCallBodyDetail.NETWORK_CALL_ID, networkCall?.id)
        intent.putExtra(ActivityNetworkCallBodyDetail.BODY_TYPE, type)
        startActivity(intent)
    }

    private fun initRequestHeaders() {
        val requestHeaderContent = findViewById<TextView>(R.id.networkCallDetailRequestHeaderContent)
        requestHeaderContent.text = getHeadersLabel(networkCall?.requestHeaders)
    }

    private fun initResponseHeaders() {
        val responseHeaderContent = findViewById<TextView>(R.id.networkCallDetailResponseHeaderContent)
        responseHeaderContent.text = getHeadersLabel(networkCall?.responseHeaders)
    }

    private fun getHeadersLabel(headersMap: Map<String, String>?) =
        if (headersMap.isNullOrEmpty()) getString(R.string.snl_no_headers_label) else toHtmlString(headersMap)

    private fun initOverview() {
        val requestOverviewContent = findViewById<TextView>(R.id.networkCallDetailOverviewContent)
        requestOverviewContent.text = toHtmlString(
            mapOf(
                "URL" to (networkCall?.url.orEmpty()),
                "Method" to (networkCall?.method.orEmpty()),
                "Response Code" to (networkCall?.responseCode?.toString().orEmpty()),
                "Duration" to (networkCall?.duration.orEmpty())
            )
        )
    }

    private fun getUriPath(): String? {
        val uri = try {
            URI(networkCall?.url)
        } catch (e: Exception) {
            null
        }
        return uri?.path
    }

    private fun initPath() {
        val networkCallPath = findViewById<TextView>(R.id.networkCallDetailPath)
        networkCallPath.text = getUriPath()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.export -> {
                showExportDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showExportDialog() {
        val exportItems = listOf(
            ExportType.Text(
                path = getPathText(),
                overview = getOverviewText(),
                requestHeaders = getRequestHeadersText(),
                requestBody = getRequestBodyText(),
                responseHeaders = getResponseHeadersText(),
                responseBody = getResponseBodyText()
            ),
            ExportType.Html(
                path = getPathText(),
                overview = getOverviewText(),
                requestHeaders = getRequestHeadersText(),
                requestBody = getRequestBodyText(),
                responseHeaders = getResponseHeadersText(),
                responseBody = getResponseBodyText()
            )
        )

        val labels = exportItems.map { it.displayName }.toTypedArray()

        AlertDialog.Builder(this)
            .setTitle("Export as")
            .setItems(labels) { _, which ->
                val selected = exportItems[which]
                shareExportedContent(
                    activity = this,
                    fileName = getUriPath() ?: "network_call_details",
                    data = selected.toFormattedString(getString(R.string.snl_lib_name)),
                    mimeType = selected.mimeType
                )
            }.show()
    }

    private fun getPathText() = findViewById<TextView>(R.id.networkCallDetailPath).text.toString()
    private fun getOverviewText() = findViewById<TextView>(R.id.networkCallDetailOverviewContent).text.toString()
    private fun getRequestHeadersText() = findViewById<TextView>(R.id.networkCallDetailRequestHeaderContent).text.toString()
    private fun getRequestBodyText() =
        if (isEmptyBody(REQUEST)) getString(R.string.snl_no_body_request_label) else tryFormattingJson(networkCall?.requestBody.orEmpty())

    private fun getResponseHeadersText() = findViewById<TextView>(R.id.networkCallDetailResponseHeaderContent).text.toString()
    private fun getResponseBodyText() =
        if (isEmptyBody(RESPONSE)) getString(R.string.snl_no_body_response_label) else tryFormattingJson(networkCall?.responseBody.orEmpty())
}