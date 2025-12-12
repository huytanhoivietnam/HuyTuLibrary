package com.dk.mylibrary.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.dk.mylibrary.R

fun Context.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    Handler(Looper.getMainLooper()).post {
        Toast.makeText(this, msg, length).show()
    }
}

private var View.lastClickTime: Long
    get() = getTag(Int.MAX_VALUE) as? Long ?: 0
    set(value) = setTag(Int.MAX_VALUE, value)

fun View.setOnClickBounceListener(
    scaleBounce: Float = 0.95f,
    duration: Long = 300, interval: Long = 500, listener: () -> Unit
) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > interval) {
            lastClickTime = currentTime
            it.scaleX = scaleBounce
            it.scaleY = scaleBounce
            it.animate().scaleX(1f).scaleY(1f).setDuration(duration).interpolator =
                BounceInterpolator()
            listener()
        }
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

inline fun <reified T : Activity> Context.addActivity(block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(block))
}

inline fun <reified T : Activity> Context.replaceActivity(block: Intent.() -> Unit = {}) {
    val i = Intent(this, T::class.java).apply(block)
    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(i)
}

fun showDialogGotoSetting(context: Context, title: String, msg: String ){
    val alertDialog = AlertDialog.Builder(context).create()
    alertDialog.setTitle(title)
    alertDialog.setMessage(msg)
    alertDialog.setButton(-1, "Go to setting" as CharSequence) { _, _ ->
        alertDialog.dismiss()
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }
    alertDialog.show()
    val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
    positiveButton?.setTextColor(ContextCompat.getColor(context, R.color.black))
}