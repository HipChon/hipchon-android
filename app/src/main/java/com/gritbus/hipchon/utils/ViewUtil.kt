package com.gritbus.hipchon.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.*
import android.net.Uri
import android.os.FileUtils
import android.provider.OpenableColumns
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.BufferedSink
import okio.source
import java.io.File
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
