package com.gritbus.hipchon.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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

fun String.asMultipart(): MultipartBody.Part {
    val file = File(this)

    return MultipartBody.Part.createFormData("file", file.path,
        file.asRequestBody("image/jpeg".toMediaTypeOrNull())
    )
}

fun String.dateToFormattedString(): String {
    val localDateTime =
        LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    return localDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
}
