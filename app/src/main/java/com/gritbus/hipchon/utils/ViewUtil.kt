package com.gritbus.hipchon.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gritbus.hipchon.R
import com.gritbus.hipchon.domain.model.Area

fun AppCompatActivity.replaceFragment(navigationViewId: Int, fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .replace(navigationViewId, fragment)
        .commit()
}

fun getAreaWithId(viewId: Int?): Area {
    return when (viewId) {
        R.id.chip_filter_area_jeju -> Area.JEJU
        R.id.chip_filter_area_sokcho_goseong -> Area.SOKCHO_GOSEONG
        R.id.chip_filter_area_gangneung -> Area.GANGNEUNG
        else -> Area.ALL
    }
}
