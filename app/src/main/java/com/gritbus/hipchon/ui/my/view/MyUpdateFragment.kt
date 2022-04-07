package com.gritbus.hipchon.ui.my.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentMyUpdateBinding
import com.gritbus.hipchon.ui.my.viewmodel.MyUpdateViewModel
import com.gritbus.hipchon.ui.my.viewmodel.MyViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.ui.MatisseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyUpdateFragment :
    BaseViewUtil.BaseFragment<FragmentMyUpdateBinding>(R.layout.fragment_my_update) {

    private val activityViewModel: MyViewModel by activityViewModels()
    private val viewModel: MyUpdateViewModel by viewModels()
    private lateinit var registerForActivity: ActivityResultLauncher<Intent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        setRegisterForActivityResult()
        setOnClickListener()
        setNicknameField()
        setObserver()
        viewModel.getUserInfo()
    }

    private fun setRegisterForActivityResult() {
        registerForActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == AppCompatActivity.RESULT_OK) {
                    val data = Matisse.obtainResult(it.data)

                    data[0]?.let { uri -> viewModel.setUserProfile(uri) }
                }
            }
    }

    private fun setOnClickListener() {
        binding.mtMyUpdate.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
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
            viewModel.userSignup()
        }
    }

    private fun setNicknameField() {
        binding.etMyUpdate.addTextChangedListener {
            viewModel.setUserNickname(it.toString())
        }
    }

    private fun setObserver() {
        viewModel.userData.observe(viewLifecycleOwner) {
            binding.etMyUpdate.setText(it.name)
            setProfileImage(it.image)
        }
        viewModel.userImage.observe(viewLifecycleOwner) {
            setProfileImage(it)
        }
        viewModel.userName.observe(viewLifecycleOwner) {
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
                "success" -> {
                    Toast.makeText(
                        requireContext(),
                        "프로필 업데이트를 성공하였습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    activityViewModel.getMyProfile()
                }
                else -> Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setProfileImage(image: String) {
        Glide.with(requireContext())
            .load(image)
            .circleCrop()
            .error(R.drawable.ic_profile_default_gray)
            .into(binding.ivMyUpdate)
    }

    private fun setProfileImage(image: Uri) {
        Glide.with(requireContext())
            .load(image)
            .circleCrop()
            .error(R.drawable.ic_profile_default_gray)
            .into(binding.ivMyUpdate)
    }
}
