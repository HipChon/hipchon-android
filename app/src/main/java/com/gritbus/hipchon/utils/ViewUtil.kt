package com.gritbus.hipchon.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlin.math.roundToInt

fun AppCompatActivity.replaceFragment(navigationViewId: Int, fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .replace(navigationViewId, fragment)
        .commit()
}

fun AppCompatActivity.addFragment(navigationViewId: Int, fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .add(navigationViewId, fragment)
        .addToBackStack(null)
        .commit()
}

fun dpToPx(context: Context, dp: Int): Int {
    val density = context.resources.displayMetrics.density
    return (dp.toFloat() * density).roundToInt()
}
