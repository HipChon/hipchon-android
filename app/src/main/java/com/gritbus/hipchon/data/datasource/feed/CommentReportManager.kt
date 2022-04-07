package com.gritbus.hipchon.data.datasource.feed

import android.content.Context
import javax.inject.Inject

class CommentReportManager @Inject constructor(
    private val context: Context
) {
    private val sharedPreferences =
        context.getSharedPreferences(COMMENT_REPORT_FILE, Context.MODE_PRIVATE)

    fun setCommentReportAllData(value: String?) {
        sharedPreferences.edit()
            .putString(COMMENT_REPORT_ID, value)
            .apply()
    }

    fun getCommentReportAllData(): String? {
        return sharedPreferences.getString(COMMENT_REPORT_ID, null)
    }

    companion object {
        const val COMMENT_REPORT_FILE =
            "com.gritbus.hipchon.data.datasource.feed COMMENT_REPORT_FILE"
        const val COMMENT_REPORT_ID = "com.gritbus.hipchon.data.datasource.feed COMMENT_REPORT_ID"
    }
}
