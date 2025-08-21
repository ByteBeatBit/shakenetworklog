package it.icemangp.shakenetworklog.data

sealed interface ExportType {
    val path: String
    val overview: String
    val requestHeaders: String
    val requestBody: String
    val responseHeaders: String
    val responseBody: String

    fun toFormattedString(appName: String): String
    val mimeType: String
    val displayName: String

    class Text(
        override val path: String,
        override val overview: String,
        override val requestHeaders: String,
        override val requestBody: String,
        override val responseHeaders: String,
        override val responseBody: String
    ) : ExportType {
        override fun toFormattedString(appName: String): String = buildString {
            appendLine("=== $appName ===\n")
            appendLine("Path:\n$path\n")
            appendLine("Overview:\n$overview\n")
            appendLine("Request Headers:\n$requestHeaders\n")
            appendLine("Request Body:\n$requestBody\n")
            appendLine("Response Headers:\n$responseHeaders\n")
            appendLine("Response Body:\n$responseBody\n")
        }

        override val mimeType: String get() = "text/plain"
        override val displayName: String get() = "Text"
    }

    class Html(
        override val path: String,
        override val overview: String,
        override val requestHeaders: String,
        override val requestBody: String,
        override val responseHeaders: String,
        override val responseBody: String
    ) : ExportType {
        override fun toFormattedString(appName: String): String = buildString {
            appendLine("<html><body style=\"font-family: monospace;\">")
            appendLine("<h2>=== $appName ===</h2>")
            appendLine("<h3>Path</h3><p>${escape(path)}</p>")
            appendLine("<h3>Overview</h3><p>${escape(overview)}</p>")
            appendLine("<h3>Request Headers</h3><pre>${escape(requestHeaders)}</pre>")
            appendLine("<h3>Request Body</h3><pre>${escape(requestBody)}</pre>")
            appendLine("<h3>Response Headers</h3><pre>${escape(responseHeaders)}</pre>")
            appendLine("<h3>Response Body</h3><pre>${escape(responseBody)}</pre>")
            appendLine("</body></html>")
        }

        override val mimeType: String get() = "text/html"
        override val displayName: String get() = "HTML"

        private fun escape(text: String): String = text
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
    }
}