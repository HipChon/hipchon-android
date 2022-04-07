package com.gritbus.hipchon.data.datasource.user

import android.content.Context
import javax.inject.Inject

class AutoLoginManager @Inject constructor(
    private val context: Context
) {
    private val sharedPreferences =
        context.getSharedPreferences(AUTO_LOGIN_FILE, Context.MODE_PRIVATE)

    fun setLoginId(value: String?) {
        sharedPreferences.edit()
            .putString(AUTO_LOGIN_ID, value)
            .apply()
    }

    fun setPlatform(value: String?) {
        sharedPreferences.edit()
            .putString(AUTO_LOGIN_PLATFORM, value)
            .apply()
    }

    fun getLoginId(): String? {
        return sharedPreferences.getString(AUTO_LOGIN_ID, null)
    }

    fun getPlatform(): String? {
        return sharedPreferences.getString(AUTO_LOGIN_PLATFORM, null)
    }

    companion object {
        private const val AUTO_LOGIN_FILE =
            "com.gritbus.hipchon.data.datasource.user AUTO_LOGIN_FILE"
        private const val AUTO_LOGIN_ID = "com.gritbus.hipchon.data.datasource.user AUTO_LOGIN_ID"
        private const val AUTO_LOGIN_PLATFORM = "com.gritbus.hipchon.data.datasource.user AUTO_LOGIN_PLATFORM"
    }
}
