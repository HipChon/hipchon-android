package com.gritbus.hipchon.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

sealed class BaseViewUtil {
    abstract class BaseFragment<T : ViewDataBinding>(private val layout: Int) : Fragment() {

        private var _binding: T? = null
        protected val binding
            get() = _binding ?: error("binding 초기화 필요")

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            _binding = DataBindingUtil.inflate(inflater, layout, container, false)
            return binding.root
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

        abstract fun initView()
    }

    abstract class BaseAppCompatActivity<T : ViewDataBinding>(private val layout: Int) :
        AppCompatActivity() {

        protected lateinit var binding: T

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = DataBindingUtil.setContentView(this, layout)
        }

        abstract fun initView()
    }
}
