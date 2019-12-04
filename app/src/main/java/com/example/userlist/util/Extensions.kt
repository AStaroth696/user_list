package com.example.userlist.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.example.userlist.R
import java.text.SimpleDateFormat
import java.util.*

private var toast: Toast? = null
@SuppressLint("ConstantLocale")
private val sourceDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
@SuppressLint("ConstantLocale")
private val targetDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    toast?.cancel()
    toast = Toast.makeText(this, message, duration)
    toast?.show()
}

fun Context.toast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
    toast?.cancel()
    toast = Toast.makeText(this, message, duration)
    toast?.show()
}

fun log(message: String, tag: String = "USER_LIST_APP") {
    Log.v(tag, message)
}

fun <T> T?.checkNull(notNull: T.() -> Unit, onNull: () -> Unit) {
    if (this != null) {
        notNull(this)
    } else {
        onNull()
    }
}

fun ImageView.loadCircleImage(imageUrl: String?) {
    Glide.with(this)
        .load(imageUrl)
        .placeholder(R.drawable.user_photo_placeholder)
        .circleCrop()
        .into(this)
}

fun Int.dpToPixels() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun String.reformatDate(): String {
    return try {
        targetDateFormat.format(sourceDateFormat.parse(this))
    } catch (e: Exception) {
        this
    }
}