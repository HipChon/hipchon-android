package com.gritbus.hipchon.data.datasource.user

import android.content.Context
import javax.inject.Inject

class UserReportManager @Inject constructor(
    private val context: Context
) {
    private val sharedPreferences =
        context.getSharedPreferences(USER_REPORT_FILE, Context.MODE_PRIVATE)

    fun setUserReportAllData(value: String?) {
        sharedPreferences.edit()
            .putString(USER_REPORT_ID, value)
            .apply()
    }

    fun getUserReportAllData(): String? {
        return sharedPreferences.getString(USER_REPORT_ID, null)
    }

    companion object {
        const val USER_REPORT_FILE =
            "com.gritbus.hipchon.data.datasource.user USER_REPORT_FILE"
        const val USER_REPORT_ID = "com.gritbus.hipchon.data.datasource.user USER_REPORT_ID"
    }
}
