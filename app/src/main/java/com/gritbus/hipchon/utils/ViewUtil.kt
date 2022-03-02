package com.gritbus.hipchon.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gritbus.hipchon.R
import com.gritbus.hipchon.domain.model.Area
import com.gritbus.hipchon.domain.model.Hashtag
import kotlin.math.roundToInt

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

fun getHashtagWithId(viewId: Int?): Hashtag {
    return when (viewId) {
        R.id.chip_filter_type_fire -> Hashtag.FIRE
        R.id.chip_filter_type_water -> Hashtag.WATER
        R.id.chip_filter_type_field -> Hashtag.FIELD
        R.id.chip_filter_type_vacation -> Hashtag.VACATION
        else -> Hashtag.NOTHING
    }
}

fun dpToPx(context: Context, dp: Int): Int {
    val density = context.resources.displayMetrics.density
    return (dp.toFloat() * density).roundToInt()
}
