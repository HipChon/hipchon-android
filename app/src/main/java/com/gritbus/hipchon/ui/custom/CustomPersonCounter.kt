package com.gritbus.hipchon.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.CustomPersonCounterBinding

class CustomPersonCounter constructor(
    context: Context, attrs: AttributeSet
) : ConstraintLayout(context, attrs) {

    private var count: Int = 1

    private var binding: CustomPersonCounterBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.custom_person_counter,
        this,
        true
    )

    init {
        setMinusPersonCounter()
        setPlusPersonCounter()
    }

    private fun setMinusPersonCounter() {
        binding.ivPersonCounterMinus.setOnClickListener {
            if (count > 1) {
                count -= 1
            }
            refreshPersonCounterView()
        }
    }

    private fun setPlusPersonCounter() {
        binding.ivPersonCounterPlus.setOnClickListener {
            if (count < 9) {
                count += 1
            }
            refreshPersonCounterView()
        }
    }

    private fun refreshPersonCounterView() {
        when (count) {
            1 -> {
                binding.tvPersonCounter.text = resources.getString(R.string.person_counter_one)
                binding.ivPersonCounterPlus.setImageResource(R.drawable.bg_plus)
                binding.tvPersonCounterHelp.visibility = View.INVISIBLE
            }
            else -> {
                binding.tvPersonCounter.text =
                    resources.getString(R.string.person_counter_over_one, count)
                binding.ivPersonCounterPlus.setImageResource(R.drawable.bg_plus_filled)
                binding.tvPersonCounterHelp.visibility = View.VISIBLE
            }
        }
    }

    fun getPersonCount(): Int {
        return count
    }
}
