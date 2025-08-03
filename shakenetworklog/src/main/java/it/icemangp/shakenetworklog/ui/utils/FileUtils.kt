package it.icemangp.shakenetworklog.ui.utils

import android.app.Activity
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

object FileUtils {

    private fun fileNameFormatter(fileName: String) = fileName.replace("/", "_")

    private fun getTodayAsYYYYmmdd(): String {
        val formatter = SimpleDateFormat("yyyyMMdd_HHmmss")
        return formatter.format(Date())
    }

    fun shareExportedContent(activity: Activity, fileName: String, data: String, mimeType: String) {
        val file = File(activity.cacheDir, "api_${fileNameFormatter(fileName)}_${getTodayAsYYYYmmdd()}.${if (mimeType == "text/html") "html" else "txt"}")
        file.writeText(data)

        val uri = FileProvider.getUriForFile(
            activity,
            "${activity.packageName}.fileprovider",
            file
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = mimeType
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        activity.startActivity(Intent.createChooser(intent, "Share log"))
    }

}