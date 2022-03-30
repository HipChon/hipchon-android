package com.gritbus.hipchon.utils

import android.content.Context
import android.graphics.*
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

fun Bitmap.getCircledBitmap(): Bitmap {
    val output = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)
    val paint = Paint()
    val rect = Rect(0, 0, this.width, this.height)
    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    canvas.drawCircle(this.width / 2f, this.height / 2f, this.width / 2f, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(this, rect, rect, paint)
    return output
}