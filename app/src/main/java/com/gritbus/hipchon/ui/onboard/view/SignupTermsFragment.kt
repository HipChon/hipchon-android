package com.gritbus.hipchon.ui.onboard.view

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentSignupTermsBinding
import com.gritbus.hipchon.ui.onboard.viewmodel.SignupViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupTermsFragment :
    BaseViewUtil.BaseFragment<FragmentSignupTermsBinding>(R.layout.fragment_signup_terms) {

    private val viewModel: SignupViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        setClickListener()
        setObserver()
    }

    private fun setClickListener() {
        binding.mbSignupTerms.setOnClickListener {
//            (activity as? SignupActivity)?.moveToNextLevel(ProfileFragment())
        }
        binding.ivSignupTermsAll.setOnClickListener {
            viewModel.updateTermsStatus(TERM_ALL)
        }
        binding.ivSignupTermsAge.setOnClickListener {
            viewModel.updateTermsStatus(TERM_AGE)
        }
        binding.ivSignupTermsService.setOnClickListener {
            viewModel.updateTermsStatus(TERM_SERVICE)
        }
        binding.ivSignupTermsServiceMore.setOnClickListener {
            // 약관페이지
        }
        binding.ivSignupTermsPersonal.setOnClickListener {
            viewModel.updateTermsStatus(TERM_PERSONAL)
        }
        binding.ivSignupTermsPersonalMore.setOnClickListener {
            // 약관페이지
        }
        binding.ivSignupTermsPosition.setOnClickListener {
            viewModel.updateTermsStatus(TERM_POSITION)
        }
        binding.ivSignupTermsPositionMore.setOnClickListener {
            // 약관페이지
        }
        binding.ivSignupTermsEvent.setOnClickListener {
            viewModel.updateTermsStatus(TERM_EVENT)
        }
    }

    private fun setObserver() {
        viewModel.isMovable.observe(viewLifecycleOwner) {
            if (it) {
                binding.mbSignupTerms.isEnabled = true
                binding.mbSignupTerms.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.primary_green
                    )
                )
            } else {
                binding.mbSignupTerms.isEnabled = false
                binding.mbSignupTerms.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray01))
            }
        }

        viewModel.allTerms.observe(viewLifecycleOwner) {
            setCheckTint(binding.ivSignupTermsAll, it)
        }
        viewModel.ageTerms.observe(viewLifecycleOwner) {
            setCheckTint(binding.ivSignupTermsAge, it)
        }
        viewModel.serviceTerms.observe(viewLifecycleOwner) {
            setCheckTint(binding.ivSignupTermsService, it)
        }
        viewModel.personalTerms.observe(viewLifecycleOwner) {
            setCheckTint(binding.ivSignupTermsPersonal, it)
        }
        viewModel.positionTerms.observe(viewLifecycleOwner) {
            setCheckTint(binding.ivSignupTermsPosition, it)
        }
        viewModel.eventTerms.observe(viewLifecycleOwner) {
            setCheckTint(binding.ivSignupTermsEvent, it)
        }
    }

    private fun setCheckTint(checkView: ImageView, isActive: Boolean) {
        val color = if (isActive) R.color.primary_green else R.color.gray03
        checkView.imageTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                requireContext(),
                color
            )
        )
    }

    companion object {
        const val TERM_ALL = "TERM_ALL"
        const val TERM_AGE = "TERM_AGE"
        const val TERM_SERVICE = "TERM_SERVICE"
        const val TERM_PERSONAL = "TERM_PERSONAL"
        const val TERM_POSITION = "TERM_POSITION"
        const val TERM_EVENT = "TERM_EVENT"
    }
}
