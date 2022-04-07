package com.gritbus.hipchon.ui.my.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.UserData
import com.gritbus.hipchon.databinding.ActivityMySettingBinding
import com.gritbus.hipchon.ui.my.viewmodel.MySettingViewModel
import com.gritbus.hipchon.ui.onboard.view.OnboardingActivity
import com.gritbus.hipchon.ui.onboard.view.OnboardingActivity.Companion.PLATFORM_KAKAO
import com.gritbus.hipchon.ui.onboard.view.OnboardingActivity.Companion.PLATFORM_NAVER
import com.gritbus.hipchon.utils.BaseViewUtil
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MySettingActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityMySettingBinding>(R.layout.activity_my_setting) {

    private val viewModel: MySettingViewModel by viewModels()
    private val suggestUrl = "https://bit.ly/3qRHltE"
    private val noticeUrl = "https://bit.ly/3uJWD4v"
    private val faqUrl = "https://bit.ly/3LrIjV3"
    private val centerUrl = "http://pf.kakao.com/_xgHYNb"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        setObserver()
        setOnClickListener()
    }

    private fun setObserver() {
        viewModel.isLogoutSuccess.observe(this) {
            if (it) {
                when (UserData.platform) {
                    PLATFORM_NAVER -> {
                        if (UserData.userLoginId != "masterId"){
                            NidOAuthLogin().logout()
                            startActivity(Intent(baseContext, OnboardingActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            })
                        } else {
                            startActivity(Intent(baseContext, OnboardingActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            })
                        }
                    }
                    PLATFORM_KAKAO -> {
                        UserApiClient.instance.logout { error ->
                            if (error != null) {
                                Log.e(this.javaClass.name, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                            } else {
                                Log.i(this.javaClass.name, "로그아웃 성공. SDK에서 토큰 삭제됨")
                                startActivity(Intent(baseContext, OnboardingActivity::class.java).apply {
                                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                })
                            }
                        }
                    }
                }
            }
        }
        viewModel.isLeaveSuccess.observe(this) {
            if (it) {
                when (UserData.platform) {
                    PLATFORM_NAVER -> {
                        if (UserData.userLoginId != "masterId"){
                            NidOAuthLogin().callDeleteTokenApi(this, object : OAuthLoginCallback {
                                override fun onSuccess() {
                                    Log.i(this.javaClass.name, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                                    startActivity(Intent(baseContext, OnboardingActivity::class.java).apply {
                                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    })
                                }
                                override fun onFailure(httpStatus: Int, message: String) {
                                    Log.d(this.javaClass.name, "errorCode: ${NaverIdLoginSDK.getLastErrorCode().code}")
                                    Log.d(this.javaClass.name, "errorDesc: ${NaverIdLoginSDK.getLastErrorDescription()}")
                                }
                                override fun onError(errorCode: Int, message: String) {
                                    onFailure(errorCode, message)
                                }
                            })
                        } else {
                            startActivity(Intent(baseContext, OnboardingActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            })
                        }
                    }
                    PLATFORM_KAKAO -> {
                        UserApiClient.instance.unlink { error ->
                            if (error != null) {
                                Log.e(this.javaClass.name, "연결 끊기 실패", error)
                            } else {
                                Log.i(this.javaClass.name, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                                startActivity(Intent(baseContext, OnboardingActivity::class.java).apply {
                                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                })
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setOnClickListener() {
        binding.mtMySetting.setOnClickListener {
            finish()
        }
        binding.tvMySettingAppAccount.setOnClickListener {
            startActivity(Intent(baseContext, MySettingInfoActivity::class.java))
        }
        binding.tvMySettingAppLogout.setOnClickListener {
            viewModel.logoutUser()
        }
        binding.tvMySettingAppLeave.setOnClickListener {
            viewModel.leaveUser()
        }
        binding.tvMySettingHostSuggest.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(suggestUrl)))
        }
        binding.tvMySettingCenterNotice.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(noticeUrl)))
        }
        binding.tvMySettingCenterQna.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(faqUrl)))
        }
        binding.tvMySettingCenterQuestion.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(centerUrl)))
        }
    }
}
