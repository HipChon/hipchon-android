package com.gritbus.hipchon.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.replaceFragment(navigationViewId: Int, fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .replace(navigationViewId, fragment)
        .commit()
}
