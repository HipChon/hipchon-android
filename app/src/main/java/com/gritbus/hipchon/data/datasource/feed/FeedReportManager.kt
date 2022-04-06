package com.gritbus.hipchon.data.datasource.feed

import android.content.Context
import javax.inject.Inject

class FeedReportManager @Inject constructor(
    private val context: Context
) {
    private val sharedPreferences =
        context.getSharedPreferences(FEED_REPORT_FILE, Context.MODE_PRIVATE)

    fun setFeedReportAllData(value: String?) {
        sharedPreferences.edit()
            .putString(FEED_REPORT_ID, value)
            .apply()
    }

    fun getFeedReportAllData(): String? {
        return sharedPreferences.getString(FEED_REPORT_ID, null)
    }

    companion object {
        const val FEED_REPORT_FILE =
            "com.gritbus.hipchon.data.datasource.feed COMMENT_REPORT_FILE"
        const val FEED_REPORT_ID = "com.gritbus.hipchon.data.datasource.feed COMMENT_REPORT_ID"
    }
}
