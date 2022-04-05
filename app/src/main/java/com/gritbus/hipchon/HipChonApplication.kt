package com.gritbus.hipchon

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import com.naver.maps.map.NaverMapSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.log.NidLog
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HipChonApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        NidLog.init()
        NaverIdLoginSDK.initialize(
            this,
            BuildConfig.NAVER_CLIENT_ID,
            BuildConfig.NAVER_CLIENT_SECRET,
            BuildConfig.NAVER_APP_NAME
        )
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(BuildConfig.MAP_CLIENT_ID)
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
    }
}
