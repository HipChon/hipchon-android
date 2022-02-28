package com.gritbus.hipchon.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.google.android.material.chip.ChipGroup
import com.gritbus.hipchon.R
import com.gritbus.hipchon.domain.model.Area
import com.gritbus.hipchon.ui.home.HomeQuickSearchViewModel

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

@BindingAdapter("onCheckedChanged")
fun setOnCheckedChanged(view: ChipGroup, viewModel: ViewModel) {
    view.setOnCheckedChangeListener { _, checkedId ->
        (viewModel as? HomeQuickSearchViewModel)?.setArea(checkedId)
    }
}

@BindingAdapter("checkedArea")
fun setCheckedArea(view: ChipGroup, area: Area) {
    when (area) {
        Area.ALL -> view.clearCheck()
        Area.JEJU -> view.check(R.id.chip_filter_area_jeju)
        Area.SOKCHO_GOSEONG -> view.check(R.id.chip_filter_area_sokcho_goseong)
        Area.GANGNEUNG -> view.check(R.id.chip_filter_area_gangneung)
    }
}
