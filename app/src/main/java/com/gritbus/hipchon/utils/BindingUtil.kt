package com.gritbus.hipchon.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.google.android.material.chip.ChipGroup
import com.gritbus.hipchon.R
import com.gritbus.hipchon.domain.model.Area
import com.gritbus.hipchon.domain.model.FilterType
import com.gritbus.hipchon.domain.model.Hashtag
import com.gritbus.hipchon.ui.home.viewmodel.HomeQuickSearchViewModel

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

@BindingAdapter("viewModel", "type")
fun setOnCheckedChanged(view: ChipGroup, viewModel: ViewModel, type: FilterType) {
    view.setOnCheckedChangeListener { _, checkedId ->
        when (type) {
            FilterType.AREA -> (viewModel as? HomeQuickSearchViewModel)?.setArea(checkedId)
            FilterType.HASHTAG -> (viewModel as? HomeQuickSearchViewModel)?.setHashtag(checkedId)
        }
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

@BindingAdapter("checkedHashtag")
fun setCheckedHashtag(view: ChipGroup, hashtag: Hashtag) {
    when (hashtag) {
        Hashtag.NOTHING -> view.clearCheck()
        Hashtag.FIRE -> view.check(R.id.chip_filter_type_fire)
        Hashtag.WATER -> view.check(R.id.chip_filter_type_water)
        Hashtag.FIELD -> view.check(R.id.chip_filter_type_field)
        Hashtag.VACATION -> view.check(R.id.chip_filter_type_vacation)
    }
}

