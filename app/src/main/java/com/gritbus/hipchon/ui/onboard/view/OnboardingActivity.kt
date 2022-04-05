package com.gritbus.hipchon.ui.onboard.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.gritbus.hipchon.BuildConfig
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityOnboardingBinding
import com.gritbus.hipchon.ui.MainActivity
import com.gritbus.hipchon.ui.onboard.viewmodel.OnboardingViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.log.NidLog
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {

    private val viewModel: OnboardingViewModel by viewModels()
    private val kakaoChannel = "https://pf.kakao.com/_xgHYNb"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        setClickListener()
        setObserver()
    }

    private fun setClickListener() {
        binding.mbOnboardingNaver.setOnClickListener {
            setNaverLogin()
        }
        binding.mbOnboardingKakao.setOnClickListener {
            setKakaoLogin()
        }
        binding.tvOnboardingProblem.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(kakaoChannel)))
        }
        binding.ivOnboardingHipchoni.setOnClickListener {
            viewModel.updateHipchoniCount()
        }
    }

    private fun setObserver() {
        viewModel.isLoginSuccess.observe(this) {
            if (it){
                moveToMainActivity()
            } else {
                moveToSignupActivity()
            }
        }
        viewModel.hipchoniCount.observe(this) {
            if (it == 10) {
                viewModel.loginWithMasterAccount()
            }
        }
    }

    private fun setNaverLogin() {
        NidLog.init()
        NaverIdLoginSDK.initialize(
            this,
            BuildConfig.NAVER_CLIENT_ID,
            BuildConfig.NAVER_CLIENT_SECRET,
            BuildConfig.NAVER_APP_NAME
        )

        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDesc = NaverIdLoginSDK.getLastErrorDescription()
                Log.e("naver login error : ", "errorCode:$errorCode, errorDesc:$errorDesc")
            }

            override fun onSuccess() {
                getNaverProfile()
            }
        }
        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }

    private fun getNaverProfile() {
        NidOAuthLogin().callProfileApi(object: NidProfileCallback<NidProfileResponse> {
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDesc = NaverIdLoginSDK.getLastErrorDescription()
                Log.e("naver login error : ", "errorCode:$errorCode, errorDesc:$errorDesc")
            }

            override fun onSuccess(result: NidProfileResponse) {
                checkNaverLogin(result.profile?.id)
            }
        })
    }

    private fun checkNaverLogin(id: String?) {
        id?.let {
            viewModel.userLogin(it, PLATFORM_NAVER)
        } ?: moveToSignupActivity()
    }

    private fun moveToMainActivity() {
        startActivity(Intent(baseContext, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }

    private fun moveToSignupActivity() {
        startActivity(Intent(baseContext, SignupActivity::class.java))
    }

    private fun setKakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("kakao login error", "카카오 로그인 실패", error)
            } else if (token != null) {
                UserApiClient.instance.me { user, error ->
                    if (user != null) {
                        user.id?.let { viewModel.userLogin(it.toString(), PLATFORM_KAKAO) }
                    }
                }
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@OnboardingActivity)) {
            UserApiClient.instance.loginWithKakaoTalk(this@OnboardingActivity, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this@OnboardingActivity, callback = callback)
        }
    }

    companion object {
        const val PLATFORM_NAVER = "naver"
        const val PLATFORM_KAKAO = "kakao"
    }
}
