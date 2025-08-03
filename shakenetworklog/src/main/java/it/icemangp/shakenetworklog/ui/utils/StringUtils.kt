package it.icemangp.shakenetworklog.ui.utils

import android.text.Spanned
import androidx.core.text.HtmlCompat

object StringUtils {
    fun toHtmlString(map: Map<String, String>): Spanned {
        val builder = StringBuilder()

        map.onEachIndexed { index, entry ->
            builder.append("<b>${entry.key}</b>: ${entry.value}")
            if (index != map.size - 1) builder.append("<br>")
        }
        return HtmlCompat.fromHtml(
            builder.toString(),
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )
    }
}