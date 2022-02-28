package com.gritbus.hipchon.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.gritbus.hipchon.R

@BindingAdapter("countText")
fun setCountText(view: TextView, count: Int) {
    view.text = when (count) {
        1 -> view.context.resources.getString(R.string.person_counter_one)
        else -> view.context.resources.getString(R.string.person_counter_over_one, count)
    }
}

@BindingAdapter("visible")
fun setVisible(view: TextView, count: Int) {
    view.visibility = when (count) {
        1 -> View.INVISIBLE
        else -> View.VISIBLE
    }
}
