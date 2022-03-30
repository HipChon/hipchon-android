package com.gritbus.hipchon.ui.onboard.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.UserData
import com.gritbus.hipchon.databinding.FragmentMyUpdateBinding
import com.gritbus.hipchon.ui.onboard.view.OnboardingActivity.Companion.PLATFORM_KAKAO
import com.gritbus.hipchon.ui.onboard.view.OnboardingActivity.Companion.PLATFORM_NAVER
import com.gritbus.hipchon.ui.onboard.viewmodel.SignupViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.ui.MatisseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupProfileFragment :
    BaseViewUtil.BaseFragment<FragmentMyUpdateBinding>(R.layout.fragment_my_update) {

    private val viewModel: SignupViewModel by viewModels()
    private lateinit var registerForActivity: ActivityResultLauncher<Intent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        setRegisterForActivityResult()
        setToolbarVisibility()
        setBaseProfileInfoFromSocial()
        setOnClickListener()
    }

    private fun setRegisterForActivityResult() {
        registerForActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == AppCompatActivity.RESULT_OK) {
                    val data = Matisse.obtainResult(it.data)

                    Glide.with(requireContext())
                        .load(data[0])
                        .circleCrop()
                        .into(binding.ivMyUpdate)
                }
            }
    }

    private fun setToolbarVisibility() {
        binding.mtMyUpdate.visibility = View.GONE
    }

    private fun setBaseProfileInfoFromSocial() {
        when (UserData.platform) {
            PLATFORM_NAVER -> {
                setProfileFromNaver()
            }
            PLATFORM_KAKAO -> {
                setProfileFromKakao()
            }
        }
    }

    private fun setProfileFromNaver() {
        NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDesc = NaverIdLoginSDK.getLastErrorDescription()
                Log.e("naver login error : ", "errorCode:$errorCode, errorDesc:$errorDesc")
            }

            override fun onSuccess(result: NidProfileResponse) {
                Log.i("result", result.profile.toString())
                result.profile?.profileImage?.let {
                    Glide.with(requireContext())
                        .load(it)
                        .into(binding.ivMyUpdate)
                }
                result.profile?.nickname?.let {
                    binding.etMyUpdate.setText(it)
                }
            }
        })
    }

    private fun setProfileFromKakao() {
        UserApiClient.instance.me { user, error ->
            user?.kakaoAccount?.profile?.profileImageUrl?.let {
                Glide.with(requireContext())
                    .load(it)
                    .into(binding.ivMyUpdate)
            }
            user?.kakaoAccount?.profile?.nickname?.let {
                binding.etMyUpdate.setText(it)
            }
        }
    }

    private fun setOnClickListener() {
        binding.ivMyUpdate.setOnClickListener {
            // 프로필 이미지 변경
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Matisse.from(this)
                    .choose(MimeType.ofAll())
                    .countable(true)
                    .maxSelectable(1)
                    .imageEngine(GlideEngine())
                    .let {
                        val intent = Intent(requireContext(), MatisseActivity::class.java)
                        registerForActivity.launch(intent)
                    }
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    1000
                )
            }
        }
        binding.acbMyUpdate.setOnClickListener {
            // 회원가입 처리
        }
    }
}
