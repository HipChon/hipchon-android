package com.gritbus.hipchon.ui.onboard.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
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

    private val viewModel: SignupViewModel by activityViewModels()
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
        setObserver()
        setNicknameField()
    }

    private fun setRegisterForActivityResult() {
        registerForActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == AppCompatActivity.RESULT_OK) {
                    val data = Matisse.obtainResult(it.data)

                    data[0]?.let { uri -> viewModel.setUserProfilePath(uri) }
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
//                result.profile?.profileImage?.let {
//                    viewModel.setUserProfilePath(Uri.parse(it))
//                }
                result.profile?.nickname?.let {
                    viewModel.setUserNickname(it)
                    binding.etMyUpdate.setText(it)
                }
                result.profile?.email?.let {
                    viewModel.setUserEmail(it)
                }
            }
        })
    }

    private fun setProfileFromKakao() {
        UserApiClient.instance.me { user, error ->
//            user?.kakaoAccount?.profile?.profileImageUrl?.let {
//                viewModel.setUserProfilePath(Uri.parse(it))
//            }
            user?.kakaoAccount?.profile?.nickname?.let {
                viewModel.setUserNickname(it)
                binding.etMyUpdate.setText(it)
            }
            user?.kakaoAccount?.email?.let {
                viewModel.setUserEmail(it)
            }
        }
    }

    private fun setOnClickListener() {
        binding.ivMyUpdate.setOnClickListener {
            // ????????? ????????? ??????
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
            viewModel.userSignup()
        }
    }

    private fun setObserver() {
        viewModel.profilePath.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it)
                .circleCrop()
                .into(binding.ivMyUpdate)
        }
        viewModel.nickname.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                binding.acbMyUpdate.isEnabled = true
                binding.acbMyUpdate.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.primary_green
                    )
                )
            } else {
                binding.acbMyUpdate.isEnabled = false
                binding.acbMyUpdate.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray02))
            }
        }
        viewModel.isSignupSuccess.observe(viewLifecycleOwner) {
            when (it) {
                "success" -> viewModel.getUserId()
                else -> Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.hasUserId.observe(viewLifecycleOwner) {
            if (it) {
                (activity as SignupActivity).moveToMain()
            }
        }
    }

    private fun setNicknameField() {
        binding.etMyUpdate.addTextChangedListener {
            viewModel.setUserNickname(it.toString())
        }
    }
}
