package it.icemangp.shakenetworklog.ui.utils

import org.json.JSONArray
import org.json.JSONObject

object JsonUtils {

    fun tryFormattingJson(body: String): String = tryFormattingJsonObject(body) ?: tryFormattingJsonArray(body).orEmpty()

    private fun tryFormattingJsonObject(body: String): String? {
        return try {
            val json = JSONObject(body)
            json.toString(4)
        } catch (e: Exception) {
            null
        }
    }

    private fun tryFormattingJsonArray(body: String): String? {
        return try {
            val jsonArray = JSONArray(body)
            jsonArray.toString(4)
        } catch (e: Exception) {
            null
        }
    }
}